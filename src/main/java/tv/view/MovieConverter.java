package tv.view;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;

import tv.db.MovieEntity;
import tv.openwebif.OpenWebifService;

@Component
public class MovieConverter {

  private static final ImmutableMap<String, Integer> TV_SPIELFILM_RANK_MAP = ImmutableMap.of("1", 60, "2", 40, "3", 20);

  private static final ImmutableMap<Range<Integer>, RatingClass> METACRITIC_CLASS_MAPPING = ImmutableMap
      .<Range<Integer>, RatingClass> builder()
      .put(Range.lessThan(40), RatingClass.BAD)
      .put(Range.closedOpen(40, 60), RatingClass.MEDIUM)
      .put(Range.atLeast(60), RatingClass.GOOD)
      .build();

  public MovieViewModel toViewModel(MovieEntity entity) {
    List<AiringData> airingData = Lists.newArrayList(getAiringData(entity));

    boolean isOriginalTitleConflict = isConflict(entity.isNoConflict(), entity.getOriginalTitleImdb(),
        Optional.ofNullable(entity.getOriginalTitleTvSpielfilm()).orElse(entity.getTitle()));
    boolean isDirectorConflict = isConflict(entity.isNoConflict(), entity.getDirectorImdb(), entity.getDirectorTvSpielfilm());

    return MovieViewModel.builder()
        .withId(airingData.stream().findFirst().map(AiringData::getId).orElse(null))
        .withCallDate(entity.getCallDate())
        .withAiringDatas(airingData)
        .withCountry(entity.getCountry())
        .withCountries(Lists.newArrayList(Strings.nullToEmpty(entity.getCountry()).split("/")))
        .withGenre(entity.getGenre())
        .withImdbRating(entity.getImdbRating())
        .withImdbUrl(
            Optional.ofNullable(entity.getImdbId()).map(id -> String.format("https://www.imdb.com/title/%s/", id)).orElse(null))
        .withTvSpielfilmDetailUrl(entity.getTvSpielfilmDetailUrl())
        .withMetacriticRating(entity.getMetacriticRating())
        .withMetacriticClass(getMetaCriticClass(entity.getMetacriticRating()).toString())
        .withRanking(
            getRanking(entity.getTvSpielfilmRating(), entity.isTipp(), entity.getImdbRating(), entity.getMetacriticRating()))
        .withStars(entity.getStars())
        .withTitle(entity.getTitle())
        .withTvSpielfilmRating(entity.getTvSpielfilmRating())
        .withYear(entity.getYear())
        .withOriginalTitle(entity.getOriginalTitleImdb())
        .withOriginalTitleConflict(isOriginalTitleConflict)
        .withDirector(entity.getDirectorTvSpielfilm())
        .withDirectorConflict(isDirectorConflict)
        .withImages(entity.getImages())
        .withDescription(entity.getDescription())
        .withTipp(entity.isTipp())
        .withIsNew(entity.isNew())
        .withAwards(StringUtils.removeEnd(entity.getAwards(), "."))
        .withSuccess(entity.isSuccess())
        .build();
  }

  private boolean isConflict(boolean noConflict, String imdbData, String tvSpielfilmData) {
    if (noConflict || Strings.isNullOrEmpty(imdbData) || Strings.isNullOrEmpty(tvSpielfilmData)) {
      return false;
    }
    String cleanedImdbData = cleanStringForConflictedCompare(imdbData);
    String cleanedTvSpielfilmData = cleanStringForConflictedCompare(tvSpielfilmData);
    return !cleanedImdbData.equals(cleanedTvSpielfilmData);
  }

  private String cleanStringForConflictedCompare(String string) {
    return string.replaceAll("\\(.*?\\)", "").replaceAll("&.*?;", "").replaceAll("[^a-zA-Z0-9]", "").trim().toLowerCase();
  }

  public AiringData getAiringData(MovieEntity entity) {
    LocalDate callDate = LocalDate.parse(entity.getCallDate());

    int year = callDate.getYear();
    String date = new StringBuilder(entity.getDate()).insert(2, '.').toString();
    String time = entity.getTime();

    LocalDateTime startTime;
    try {
      startTime = LocalDateTime.parse(year + date + time.substring(0, 5),
          DateTimeFormatter.ofPattern("yyyyEE dd.MM.HH:mm", Locale.GERMAN));
    } catch (DateTimeException e) {
      year++;
      startTime = LocalDateTime.parse(year + date + time.substring(0, 5),
          DateTimeFormatter.ofPattern("yyyyEE dd.MM.HH:mm", Locale.GERMAN));
    }

    LocalDateTime endTime = LocalDateTime.parse(year + date + time.substring(8),
        DateTimeFormatter.ofPattern("yyyyEE dd.MM.HH:mm", Locale.GERMAN));

    if (endTime.isBefore(startTime)) {
      endTime = endTime.plusDays(1);
    }

    return AiringData.builder()
        .withId(entity.getId())
        .withChannel(entity.getChannel())
        .withDate(date)
        .withTime(time)
        .withStart(startTime)
        .withEnd(endTime)
        .withCanRecord(OpenWebifService.TVS_TO_RECORD_CHANNEL.get(entity.getChannel()) != null)
        .withRecorded(entity.isRecorded())
        .build();
  }

  private int getRanking(String tvSpielfilmRating, boolean tipp, String imdbRating, String metaCriticRating) {
    int imdbRank = (int) (Double.parseDouble(Optional.ofNullable(Strings.emptyToNull(imdbRating)).orElse("6.0")) * 10);
    int metaCriticRank = Integer.parseInt(Optional.ofNullable(Strings.emptyToNull(metaCriticRating)).orElse("60"));
    int tvSpielfilmRank = TV_SPIELFILM_RANK_MAP.getOrDefault(tvSpielfilmRating, 60);

    return imdbRank + metaCriticRank + tvSpielfilmRank;
  }

  private RatingClass getMetaCriticClass(String rating) {
    if (!Strings.isNullOrEmpty(rating)) {
      return METACRITIC_CLASS_MAPPING.keySet().stream()
          .filter(range -> range.contains(Integer.parseInt(rating)))
          .map(range -> METACRITIC_CLASS_MAPPING.get(range))
          .findFirst().get();
    }
    return RatingClass.UNKNOWN;
  }

  private enum RatingClass {
    GOOD,
    MEDIUM,
    BAD,
    UNKNOWN
  }
}
