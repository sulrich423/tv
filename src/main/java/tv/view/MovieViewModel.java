package tv.view;

import java.util.Collections;
import java.util.List;

public class MovieViewModel {

  private final Integer id;
  private final String title;
  private final String originalTitle;
  private final boolean originalTitleConflict;
  private final String country;
  private final List<String> countries;
  private final String year;
  private final String tvSpielfilmRating;
  private final String imdbRating;
  private final String metacriticRating;
  private final String metacriticClass;
  private final String director;
  private final boolean directorConflict;
  private final List<String> images;
  private final String description;
  private final List<AiringData> airingDatas;
  private final String genre;
  private final String imdbUrl;
  private final String tvSpielfilmDetailUrl;
  private final int ranking;
  private final String stars;
  private final String callDate;
  private final boolean tipp;
  private final boolean isNew;
  private final String awards;

  private MovieViewModel(Builder builder) {
    this.id = builder.id;
    this.title = builder.title;
    this.originalTitle = builder.originalTitle;
    this.originalTitleConflict = builder.originalTitleConflict;
    this.country = builder.country;
    this.countries = builder.countries;
    this.year = builder.year;
    this.tvSpielfilmRating = builder.tvSpielfilmRating;
    this.imdbRating = builder.imdbRating;
    this.metacriticRating = builder.metacriticRating;
    this.metacriticClass = builder.metacriticClass;
    this.director = builder.director;
    this.directorConflict = builder.directorConflict;
    this.images = builder.images;
    this.description = builder.description;
    this.airingDatas = builder.airingDatas;
    this.genre = builder.genre;
    this.imdbUrl = builder.imdbUrl;
    this.tvSpielfilmDetailUrl = builder.tvSpielfilmDetailUrl;
    this.ranking = builder.ranking;
    this.stars = builder.stars;
    this.callDate = builder.callDate;
    this.tipp = builder.tipp;
    this.isNew = builder.isNew;
    this.awards = builder.awards;
  }

  public Integer getId() {
    return id;
  }

  public String getTvSpielfilmDetailUrl() {
    return tvSpielfilmDetailUrl;
  }

  public String getCallDate() {
    return callDate;
  }

  public String getStars() {
    return stars;
  }

  public boolean isTipp() {
    return tipp;
  }

  public boolean isNew() {
    return isNew;
  }

  public boolean isConflict() {
    return isDirectorConflict() && isOriginalTitleConflict();
  }

  public String getDirector() {
    return director;
  }

  public boolean isDirectorConflict() {
    return directorConflict;
  }

  public String getMetacriticClass() {
    return metacriticClass;
  }

  public int getRanking() {
    return ranking;
  }

  public List<String> getCountries() {
    return countries;
  }

  public String getTitle() {
    return title;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public boolean isOriginalTitleConflict() {
    return originalTitleConflict;
  }

  public String getCountry() {
    return country;
  }

  public String getYear() {
    return year;
  }

  public String getTvSpielfilmRating() {
    return tvSpielfilmRating;
  }

  public String getImdbUrl() {
    return imdbUrl;
  }

  public String getImdbRating() {
    return imdbRating;
  }

  public String getMetacriticRating() {
    return metacriticRating;
  }

  public List<String> getImages() {
    return images;
  }

  public String getDescription() {
    return description;
  }

  public String getAwards() {
    return awards;
  }

  public List<AiringData> getAiringDatas() {
    return airingDatas;
  }

  public String getGenre() {
    return genre;
  }

  public Builder but() {
    return builder()
        .withId(id)
        .withTipp(tipp)
        .withAiringDatas(airingDatas)
        .withAwards(awards)
        .withCallDate(callDate)
        .withCountry(country)
        .withCountries(countries)
        .withDescription(description)
        .withDirector(director)
        .withDirectorConflict(directorConflict)
        .withGenre(genre)
        .withImages(images)
        .withImdbRating(imdbRating)
        .withImdbUrl(imdbUrl)
        .withTvSpielfilmDetailUrl(tvSpielfilmDetailUrl)
        .withMetacriticRating(metacriticRating)
        .withMetacriticClass(metacriticClass)
        .withOriginalTitle(originalTitle)
        .withOriginalTitleConflict(originalTitleConflict)
        .withRanking(ranking)
        .withStars(stars)
        .withTitle(title)
        .withTvSpielfilmRating(tvSpielfilmRating)
        .withYear(year)
        .withIsNew(isNew);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((country == null) ? 0 : country.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((year == null) ? 0 : year.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    MovieViewModel other = (MovieViewModel) obj;
    if (country == null) {
      if (other.country != null) {
        return false;
      }
    } else if (!country.equals(other.country)) {
      return false;
    }
    if (title == null) {
      if (other.title != null) {
        return false;
      }
    } else if (!title.equals(other.title)) {
      return false;
    }
    if (year == null) {
      if (other.year != null) {
        return false;
      }
    } else if (!year.equals(other.year)) {
      return false;
    }
    return true;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static Builder but(MovieViewModel movieViewModel) {
    return new Builder(movieViewModel);
  }

  public static final class Builder {
    private Integer id;
    private String title;
    private String originalTitle;
    private boolean originalTitleConflict;
    private String country;
    private List<String> countries = Collections.emptyList();
    private String year;
    private String tvSpielfilmRating;
    private String imdbRating;
    private String metacriticRating;
    private String metacriticClass;
    private String director;
    private boolean directorConflict;
    private List<String> images = Collections.emptyList();
    private String description;
    private List<AiringData> airingDatas = Collections.emptyList();
    private String genre;
    private String imdbUrl;
    private String tvSpielfilmDetailUrl;
    private int ranking;
    private String stars;
    private String callDate;
    private boolean tipp;
    private boolean isNew;
    private String awards;

    private Builder() {
    }

    private Builder(MovieViewModel movieViewModel) {
      this.id = movieViewModel.id;
      this.title = movieViewModel.title;
      this.originalTitle = movieViewModel.originalTitle;
      this.originalTitleConflict = movieViewModel.originalTitleConflict;
      this.country = movieViewModel.country;
      this.countries = movieViewModel.countries;
      this.year = movieViewModel.year;
      this.tvSpielfilmRating = movieViewModel.tvSpielfilmRating;
      this.imdbRating = movieViewModel.imdbRating;
      this.metacriticRating = movieViewModel.metacriticRating;
      this.metacriticClass = movieViewModel.metacriticClass;
      this.director = movieViewModel.director;
      this.directorConflict = movieViewModel.directorConflict;
      this.images = movieViewModel.images;
      this.description = movieViewModel.description;
      this.airingDatas = movieViewModel.airingDatas;
      this.genre = movieViewModel.genre;
      this.imdbUrl = movieViewModel.imdbUrl;
      this.tvSpielfilmDetailUrl = movieViewModel.tvSpielfilmDetailUrl;
      this.ranking = movieViewModel.ranking;
      this.stars = movieViewModel.stars;
      this.callDate = movieViewModel.callDate;
      this.tipp = movieViewModel.tipp;
      this.isNew = movieViewModel.isNew;
      this.awards = movieViewModel.awards;
    }

    public Builder withId(Integer id) {
      this.id = id;
      return this;
    }

    public Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder withOriginalTitle(String originalTitle) {
      this.originalTitle = originalTitle;
      return this;
    }

    public Builder withOriginalTitleConflict(boolean originalTitleConflict) {
      this.originalTitleConflict = originalTitleConflict;
      return this;
    }

    public Builder withCountry(String country) {
      this.country = country;
      return this;
    }

    public Builder withCountries(List<String> countries) {
      this.countries = countries;
      return this;
    }

    public Builder withYear(String year) {
      this.year = year;
      return this;
    }

    public Builder withTvSpielfilmRating(String tvSpielfilmRating) {
      this.tvSpielfilmRating = tvSpielfilmRating;
      return this;
    }

    public Builder withImdbRating(String imdbRating) {
      this.imdbRating = imdbRating;
      return this;
    }

    public Builder withMetacriticRating(String metacriticRating) {
      this.metacriticRating = metacriticRating;
      return this;
    }

    public Builder withMetacriticClass(String metacriticClass) {
      this.metacriticClass = metacriticClass;
      return this;
    }

    public Builder withDirector(String director) {
      this.director = director;
      return this;
    }

    public Builder withDirectorConflict(boolean directorConflict) {
      this.directorConflict = directorConflict;
      return this;
    }

    public Builder withImages(List<String> images) {
      this.images = images;
      return this;
    }

    public Builder withDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder withAiringDatas(List<AiringData> airingDatas) {
      this.airingDatas = airingDatas;
      return this;
    }

    public Builder withGenre(String genre) {
      this.genre = genre;
      return this;
    }

    public Builder withImdbUrl(String imdbUrl) {
      this.imdbUrl = imdbUrl;
      return this;
    }

    public Builder withTvSpielfilmDetailUrl(String tvSpielfilmDetailUrl) {
      this.tvSpielfilmDetailUrl = tvSpielfilmDetailUrl;
      return this;
    }

    public Builder withRanking(int ranking) {
      this.ranking = ranking;
      return this;
    }

    public Builder withStars(String stars) {
      this.stars = stars;
      return this;
    }

    public Builder withCallDate(String callDate) {
      this.callDate = callDate;
      return this;
    }

    public Builder withTipp(boolean tipp) {
      this.tipp = tipp;
      return this;
    }

    public Builder withIsNew(boolean isNew) {
      this.isNew = isNew;
      return this;
    }

    public Builder withAwards(String awards) {
      this.awards = awards;
      return this;
    }

    public MovieViewModel build() {
      return new MovieViewModel(this);
    }
  }
}
