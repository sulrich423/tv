package tv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.client.ClientBuilder;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import tv.ImdbDetailSchemaOrg.AggregateRating;
import tv.ImdbDetailSchemaOrg.Person;
import tv.ImdbSuggestResponse.Entry;
import tv.data.ImdbDetailData;
import tv.data.ImdbSuggestData;
import tv.data.TvSpielfilmDetailData;
import tv.data.TvSpielfilmOverviewData;
import tv.db.MovieEntity;
import tv.db.MovieRepository;
import tv.openwebif.OpenWebifService;
import tv.view.AiringData;
import tv.view.CategoryViewModel;
import tv.view.MovieConverter;
import tv.view.MovieViewModel;
import tv.websocket.WebSocketMessage;

@Component
public class TvComponent {

  private static final ImmutableList<String> CHANNEL_BLACK_LIST = ImmutableList.of("3PLUS", "ATV", "ATV2", "ORF1", "ORF2", "ORF3",
      "PULS4", "PULS8", "SF1", "SF2", "STTV", "BE1", "BIBEL", "ZEE-1", "EOTV", "SPORT", "DR1", "DR2", "TV2", "CPLUS", "CPLUSC",
      "CPLUSD", "C5", "EURO-S", "FRA2", "FRA3", "FRA4", "FRA5", "FRAO", "BBC1", "BBC2", "BBC4", "EURO-D", "ATV-A", "TV5", "TVB");

  private static final ImmutableList<String> GERMANY_LANGUAGE_COUNTRIES = ImmutableList.of("D", "A", "CH", "BRD", "DDR");

  private static final Pattern CHANNEL_PATTERN = Pattern.compile(".*,(.*)\\.html");

  @Inject
  private MovieConverter movieConverter;

  @Inject
  private MovieRepository movieRepository;

  @Inject
  private SimpMessageSendingOperations messagingTemplate;

  @Inject
  private OpenWebifService openWebifService;

  public ImmutableList<CategoryViewModel> getMovies(String date) {
    List<MovieViewModel> movies = Lists.newArrayList(movieRepository.findByCallDate(date)).stream()
        .map(movieConverter::toViewModel)
        .reduce(new ArrayList<MovieViewModel>(), reduceDuplicates(), (a, b) -> (ArrayList) ListUtils.union(a, b));

    List<MovieViewModel> sortedMovies = movies.stream()
        .sorted(Comparator.comparing(MovieViewModel::getRanking, Comparator.reverseOrder())
            .thenComparing(Comparator.comparing(MovieViewModel::getTitle)))
        .collect(Collectors.toList());

    Map<Boolean, List<MovieViewModel>> germanAndNotGermanMovies = sortedMovies.stream()
        .collect(Collectors.groupingBy(
            movie -> movie.getCountries().stream().allMatch(country -> GERMANY_LANGUAGE_COUNTRIES.contains(country))));

    List<MovieViewModel> notGermanMovies = ListUtils.emptyIfNull(germanAndNotGermanMovies.get(false));

    Map<Boolean, List<MovieViewModel>> seriesOrNot = notGermanMovies.stream()
        .collect(Collectors
            .partitioningBy(movie -> StringUtils.containsAny(movie.getTitle(), ':', '-') && movie.getGenre().startsWith("TV-")));

    List<MovieViewModel> noSeries = seriesOrNot.get(false);

    Map<Boolean, List<MovieViewModel>> beforeOrAfter8 = noSeries.stream()
        .collect(
            Collectors.partitioningBy(movie -> movie.getAiringDatas().stream()
                .map(a -> Integer.parseInt(a.getTime().substring(0, 2)))
                .anyMatch(a -> a >= 5 && a < 20)));

    return ImmutableList.<CategoryViewModel>builder()
        .add(CategoryViewModel.builder()
            .withCategoryName("Nach 20 Uhr")
            .withMovies(beforeOrAfter8.get(false))
            .build())
        .add(CategoryViewModel.builder()
            .withCategoryName("Vor 20 Uhr")
            .withMovies(beforeOrAfter8.get(true))
            .build())
        .add(CategoryViewModel.builder()
            .withCategoryName("TV-Reihen")
            .withMovies(seriesOrNot.get(true))
            .build())
        .add(CategoryViewModel.builder()
            .withCategoryName("Deutsche Filme")
            .withMovies(germanAndNotGermanMovies.get(true))
            .build())
        .build();
  }

  private BiFunction<ArrayList<MovieViewModel>, ? super MovieViewModel, ArrayList<MovieViewModel>> reduceDuplicates() {
    return (list, movieViewModel) -> {
      ArrayList<MovieViewModel> result = Lists.newArrayList();
      Optional<MovieViewModel> duplicateValue = list.stream()
          .filter(movieViewModel::equals)
          .findFirst();

      if (duplicateValue.isPresent()) {
        MovieViewModel combinedValue = Lists.<MovieViewModel>newArrayList(duplicateValue.get(), movieViewModel).stream()
            .sorted(Comparator.comparing(a -> a.getAiringDatas().get(0).getStart()))
            .findFirst().get()
            .but()
            .withAiringDatas(
                ListUtils.union(duplicateValue.get().getAiringDatas(), movieViewModel.getAiringDatas()).stream()
                    .sorted(Comparator.comparing(AiringData::getStart))
                    .collect(Collectors.toList()))
            .build();

        result.addAll(list.stream()
            .filter(t -> !movieViewModel.equals(t))
            .collect(Collectors.toList()));
        result.add(combinedValue);
      } else {
        result.addAll(list);
        result.add(movieViewModel);
      }

      return result;
    };
  }

  @Transactional
  public void update(String date) {
    movieRepository.deleteByCallDate(date);

    List<Document> docs = getAllTvSpielfilmDocs(1, date);

    List<MovieEntity> rawData = docs.parallelStream()
        .flatMap(this::getElements)
        .map(this::getTvSpielfilmOverviewData)
        .filter(this::filterMovies)
        .map(t -> MovieEntity.builder().withTvSpielfilmOverviewData(t))
        .map(t -> t.build())
        .collect(Collectors.toList());

    int totalSize = rawData.size();

    rawData.parallelStream()
        .map(this::addTvSpielfilmDetailData)
        .map(this::addImdbSuggestData)
        .map(this::addImdbDetailData)
        .map(entity -> entity.but().withCallDate(date).build())
        .peek(entity -> messagingTemplate.convertAndSend("/topic/movies", new WebSocketMessage(String.valueOf(totalSize))))
        .collect(Collectors.toList())
        .stream()
        .forEach(entity -> movieRepository.save(entity));

  }

  private boolean filterMovies(TvSpielfilmOverviewData tvSpielfilmOverviewData) {
    return !CHANNEL_BLACK_LIST.contains(tvSpielfilmOverviewData.getChannel());
  }

  private List<Document> getAllTvSpielfilmDocs(int page, String date) {
    List<Document> result = Lists.newArrayList();
    Document doc = getTvSpielfilmDoc(page, date);
    result.add(doc);
    int pages = doc.select(".pagination__item").size();
    if (page < pages) {
      result.addAll(getAllTvSpielfilmDocs(++page, date));
    }
    return result;
  }

  private Document getTvSpielfilmDoc(int page, String date) {
    System.out.println("tvspielfilm seite " + page);

    return jsoupGet("https://www.tvspielfilm.de/tv-programm/spielfilme/?page=" + page
        + "&order=time&date=" + date + "&freetv=1&cat%5B%5D=SP&time=day&channel=");
  }

  private Stream<Element> getElements(Document doc) {
    return doc.select("tr.hover").stream();
  }

  private TvSpielfilmOverviewData getTvSpielfilmOverviewData(Element element) {
    String channelHref = element.select(".programm-col1 a").attr("href");
    Matcher channelMatcher = CHANNEL_PATTERN.matcher(channelHref);
    String channel = null;
    if (channelMatcher.matches()) {
      channel = channelMatcher.group(1);
    }
    String time = element.select(".col-2 strong").html();
    String date = element.select(".col-2 span").html();
    String title = element.select(".col-3 a strong").text();
    String countryAndYear = element.select(".col-3 span").text().replace(title, "").trim();
    String country = null;
    String year = null;
    if (countryAndYear.length() > 4) {
      country = countryAndYear.substring(0, countryAndYear.length() - 4).trim();
      year = countryAndYear.substring(countryAndYear.length() - 4);
    }
    String genre = element.select(".col-4 span").html();
    String ratingClass = element.select(".col-6 span").attr("class");
    String rating = ratingClass.substring(ratingClass.length() - 1);
    String tvSpielfilmDetailUrl = element.select(".col-3 span a").attr("href");

    if (tvSpielfilmDetailUrl == null) {
      System.out.println();
    }

    return TvSpielfilmOverviewData.builder()
        .withChannel(channel)
        .withTime(time)
        .withDate(date)
        .withTitle(title)
        .withCountry(country)
        .withYear(year)
        .withGenre(genre)
        .withTvSpielfilmRating(rating)
        .withTvSpielfilmDetailUrl(tvSpielfilmDetailUrl)
        .build();
  }

  private MovieEntity addTvSpielfilmDetailData(MovieEntity movieEntity) {
    String tvSpielfilmDetailUrl = movieEntity.getTvSpielfilmDetailUrl();

    Document doc = jsoupGet(tvSpielfilmDetailUrl);

    String director = doc.select("section.cast dt").stream()
        .filter(element -> element.text().equals("Regie:"))
        .findFirst()
        .map(element -> element.nextElementSibling().text())
        .orElse(null);

    String originalTitle = doc.select("section.cast dt").stream()
        .filter(element -> element.text().equals("Originaltitel:"))
        .findFirst().map(element -> element.nextElementSibling().text())
        .orElse(null);

    List<String> images = doc.select(".gallery .swiper-slide > picture > img").stream()
        .map(element -> element.attr("data-src"))
        .filter(src -> src.startsWith("https://a2.tvspielfilm.de/itv_sofa/"))
        .collect(Collectors.toList());

    String description = doc.select(".broadcast-detail__description").html();

    boolean isTipp = !doc.select(".broadcast-detail__header .icon-tip").isEmpty();

    boolean isNew = !doc.select(".broadcast-detail__header .icon-new").isEmpty();

    TvSpielfilmDetailData tvSpielfilmDetailData = TvSpielfilmDetailData.builder()
        .withOriginalTitle(originalTitle)
        .withDirector(director)
        .withImages(images)
        .withDescription(description)
        .withTipp(isTipp)
        .withIsNew(isNew)
        .build();

    return movieEntity.but()
        .withTvSpielfilmDetailData(tvSpielfilmDetailData)
        .build();
  }

  private MovieEntity addImdbSuggestData(MovieEntity movieEntity) {
    String titleForSearch = Optional.ofNullable(movieEntity.getOriginalTitleTvSpielfilm()).orElse(movieEntity.getTitle());
    String year = movieEntity.getYear();
    String previousYear;
    String nextYear;
    if (NumberUtils.isCreatable(year)) {
      previousYear = String.valueOf(Integer.valueOf(year) - 1);
      nextYear = String.valueOf(Integer.valueOf(year) + 1);
    } else {
      previousYear = "";
      nextYear = "";
    }

    List<Entry> entryWithoutYear = jsonGet(createImdbSuggestUrl(titleForSearch)).getList().stream()
        .filter(e -> ImdbSuggestResponse.RELEVANT_KINDS.contains(e.getKind()))
        .collect(Collectors.toList());

    Entry entry = entryWithoutYear.stream()
        .filter(e -> year == null || year.equals(e.getYear()) || previousYear.equals(e.getYear()) || nextYear.equals(e.getYear()))
        .findFirst().orElse(entryWithoutYear.stream().findFirst().orElse(null));

    ImdbSuggestData imdbSuggestData;
    if (entry != null) {
      String imdbId = entry.getId();
      imdbSuggestData = ImdbSuggestData.builder()
          .withImdbId(imdbId)
          .build();
    } else {
      imdbSuggestData = ImdbSuggestData.builder().build();
    }

    return movieEntity.but()
        .withImdbSuggestData(imdbSuggestData)
        .build();
  }

  private MovieEntity addImdbDetailData(MovieEntity movieEntity) {
    String imdbId = movieEntity.getImdbId();

    if (imdbId != null && imdbId.startsWith("tt")) {
      String imdbUrl = "https://www.imdb.com/title/" + imdbId + "/";
      Document doc = jsoupGet(imdbUrl);

      String metaCriticRating = doc.select(".metacriticScore span").html();
      if (Strings.isNullOrEmpty(metaCriticRating)) {
        metaCriticRating = doc.select(".score-meta").html();
      }

      String awards = doc.select(".awards-blurb b").text();
      if (Strings.isNullOrEmpty(awards)) {
        String awardsString = doc.select("li[data-testid=award_information] a").text();
        if (awardsString != null && !awardsString.trim().equals("Awards")) {
          awards = awardsString;
        }
      }

      String schemaOrgString = doc.select("script[type=application/ld+json]").html();
      ImdbDetailSchemaOrg schemaOrg = getImdbDetailSchemaOrg(schemaOrgString);

      String director = schemaOrg.getDirector().stream()
          .map(Person::getName)
          .collect(Collectors.joining(", "));

      String stars = schemaOrg.getActor().stream()
          .map(Person::getName)
          .limit(2)
          .collect(Collectors.joining(", "));

      String imdbRating = Optional.ofNullable(schemaOrg.getAggregateRating())
          .map(AggregateRating::getRatingValue)
          .orElse(null);

      ImdbDetailData imdbDetailData = ImdbDetailData.builder()
          .withImdbRating(imdbRating)
          .withMetacriticRating(metaCriticRating)
          .withAwards(awards)
          .withDirector(director)
          .withStars(stars)
          .withOriginalTitle(schemaOrg.getName())
          .build();
      return movieEntity.but()
          .withImdbDetailData(imdbDetailData)
          .build();
    } else {
      return movieEntity;
    }

  }

  private ImdbDetailSchemaOrg getImdbDetailSchemaOrg(String schemaOrgString) {
    try {
      return new ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true).readValue(schemaOrgString,
          ImdbDetailSchemaOrg.class);
    } catch (IOException e) {
      e.printStackTrace();
      return new ImdbDetailSchemaOrg();
    }
  }

  private ImdbSuggestResponse jsonGet(String url) {
    System.out.println(url);
    try {
      ImdbSuggestResponse response = ClientBuilder.newClient()
          .register(JacksonJsonProvider.class)
          .target(url)
          .request()
          .header("user-agent",
              "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36")
          .buildGet()
          .invoke(ImdbSuggestResponse.class);
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      return new ImdbSuggestResponse();
    }
  }

  private String createImdbSuggestUrl(String title) {
    String urlTitle = title.substring(0, Math.min(20, title.length()));
    urlTitle = urlTitle.toLowerCase().replace("ß", "ss").replace("ö", "o").replace("ü", "u").replace("ä", "a")
        .replaceAll(" ",
            "_")
        .replaceAll("[\\?\\!\\(\\)\\-–’\\:/\\[\\]…#]", "");
    return "https://v2.sg.media-imdb.com/suggestion/" + urlTitle.substring(0, 1) + "/"
        + urlTitle + ".json";
  }

  private Document jsoupGet(String url) {
    try {
      System.out.println(url);
      return Jsoup.connect(url)
          .header("user-agent",
              "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36")
          .get();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean toggleRecord(Integer id) {
    Optional<MovieEntity> entity = movieRepository.findById(id);
    boolean success = entity
        .map(e -> e.isRecorded() ? openWebifService.timerDelete(e) : openWebifService.timerAdd(e))
        .orElse(false);

    if (success) {
      movieRepository.save(entity.get().but().withRecorded(!entity.get().isRecorded()).build());
    }

    return success;
  }

  public void updateSingle(Integer id, String url) {
    Optional<MovieEntity> entity = movieRepository.findById(id);
    if (entity.isPresent()) {
      Matcher matcher = Pattern.compile("https://(?:www|m).imdb.com/title/(.*?)/.*").matcher(url);
      if (matcher.matches()) {
        String imdbId = matcher.group(1);
        MovieEntity updatedEntity = addImdbDetailData(entity.get().but().withImdbId(imdbId).build());
        movieRepository.save(updatedEntity);
      }
    }
  }

  public void setNoConflict(Integer id) {
    Optional<MovieEntity> entity = movieRepository.findById(id);
    if (entity.isPresent()) {
      MovieEntity updatedEntity = entity.get().but().withNoConflict(true).build();
      movieRepository.save(updatedEntity);
    }
  }

}
