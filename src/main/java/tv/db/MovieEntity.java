package tv.db;

import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderColumn;

import tv.data.ImdbDetailData;
import tv.data.ImdbSuggestData;
import tv.data.TvSpielfilmDetailData;
import tv.data.TvSpielfilmOverviewData;

@Entity(name = "movie")
public class MovieEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String title;
  private String country;
  private String year;
  private String tvSpielfilmRating;
  private String imdbRating;
  private String metacriticRating;
  private String channel;
  private String time;
  private String date;
  private String genre;
  private String imdbId;
  private String stars;
  private String callDate;
  private String tvSpielfilmDetailUrl;
  private String originalTitleImdb;
  private String originalTitleTvSpielfilm;
  private String directorImdb;
  private String directorTvSpielfilm;
  private boolean tipp;
  @ElementCollection
  @OrderColumn(name = "image_id")
  private List<String> images;
  @Column(columnDefinition = "TEXT")
  private String description;
  private boolean isNew;
  private String awards;
  private boolean recorded;
  private boolean noConflict;
  private boolean success;

  private MovieEntity(Builder builder) {
    this.id = builder.id;
    this.title = builder.title;
    this.country = builder.country;
    this.year = builder.year;
    this.tvSpielfilmRating = builder.tvSpielfilmRating;
    this.imdbRating = builder.imdbRating;
    this.metacriticRating = builder.metacriticRating;
    this.channel = builder.channel;
    this.time = builder.time;
    this.date = builder.date;
    this.genre = builder.genre;
    this.imdbId = builder.imdbId;
    this.stars = builder.stars;
    this.callDate = builder.callDate;
    this.tvSpielfilmDetailUrl = builder.tvSpielfilmDetailUrl;
    this.originalTitleImdb = builder.originalTitleImdb;
    this.originalTitleTvSpielfilm = builder.originalTitleTvSpielfilm;
    this.directorImdb = builder.directorImdb;
    this.directorTvSpielfilm = builder.directorTvSpielfilm;
    this.tipp = builder.tipp;
    this.images = builder.images;
    this.description = builder.description;
    this.isNew = builder.isNew;
    this.awards = builder.awards;
    this.recorded = builder.recorded;
    this.noConflict = builder.noConflict;
    this.success = builder.success;
  }

  public MovieEntity() {

  }

  public Builder but() {
    return builder()
        .withCallDate(callDate)
        .withTipp(tipp)
        .withChannel(channel)
        .withCountry(country)
        .withDate(date)
        .withGenre(genre)
        .withId(id)
        .withImdbRating(imdbRating)
        .withImdbId(imdbId)
        .withMetacriticRating(metacriticRating)
        .withStars(stars)
        .withTime(time)
        .withTitle(title)
        .withTvSpielfilmDetailUrl(tvSpielfilmDetailUrl)
        .withTvSpielfilmRating(tvSpielfilmRating)
        .withYear(year)
        .withOriginalTitleImdb(originalTitleImdb)
        .withOriginalTitleTvSpielfilm(originalTitleTvSpielfilm)
        .withDirectorImdb(directorImdb)
        .withDirectorTvSpielfilm(directorTvSpielfilm)
        .withImages(images)
        .withDescription(description)
        .withIsNew(isNew)
        .withAwards(awards)
        .withRecorded(recorded)
        .withNoConflict(noConflict)
        .withSuccess(success);
  }

  public Integer getId() {
    return id;
  }

  public boolean isTipp() {
    return tipp;
  }

  public String getTitle() {
    return title;
  }

  public String getCountry() {
    return country;
  }

  public boolean isNew() {
    return isNew;
  }

  public String getYear() {
    return year;
  }

  public boolean isRecorded() {
    return recorded;
  }

  public String getTvSpielfilmRating() {
    return tvSpielfilmRating;
  }

  public String getImdbRating() {
    return imdbRating;
  }

  public String getMetacriticRating() {
    return metacriticRating;
  }

  public String getChannel() {
    return channel;
  }

  public String getTime() {
    return time;
  }

  public String getDate() {
    return date;
  }

  public String getAwards() {
    return awards;
  }

  public String getGenre() {
    return genre;
  }

  public String getImdbId() {
    return imdbId;
  }

  public String getStars() {
    return stars;
  }

  public String getCallDate() {
    return callDate;
  }

  public String getTvSpielfilmDetailUrl() {
    return tvSpielfilmDetailUrl;
  }

  public String getOriginalTitleImdb() {
    return originalTitleImdb;
  }

  public String getOriginalTitleTvSpielfilm() {
    return originalTitleTvSpielfilm;
  }

  public String getDirectorImdb() {
    return directorImdb;
  }

  public String getDirectorTvSpielfilm() {
    return directorTvSpielfilm;
  }

  public List<String> getImages() {
    return images;
  }

  public String getDescription() {
    return description;
  }

  public boolean isNoConflict() {
    return noConflict;
  }

  public boolean isSuccess() {
    return success;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private Integer id;
    private String title;
    private String country;
    private String year;
    private String tvSpielfilmRating;
    private String imdbRating;
    private String metacriticRating;
    private String channel;
    private String time;
    private String date;
    private String genre;
    private String imdbId;
    private String stars;
    private String callDate;
    private String tvSpielfilmDetailUrl;
    private String originalTitleImdb;
    private String originalTitleTvSpielfilm;
    private String directorImdb;
    private String directorTvSpielfilm;
    private boolean tipp;
    private List<String> images = Collections.emptyList();
    private String description;
    private boolean isNew;
    private String awards;
    private boolean recorded;
    private boolean noConflict;
    private boolean success = true;

    private Builder() {
    }

    public Builder withId(Integer id) {
      this.id = id;
      return this;
    }

    public Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder withCountry(String country) {
      this.country = country;
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

    public Builder withChannel(String channel) {
      this.channel = channel;
      return this;
    }

    public Builder withTime(String time) {
      this.time = time;
      return this;
    }

    public Builder withDate(String date) {
      this.date = date;
      return this;
    }

    public Builder withGenre(String genre) {
      this.genre = genre;
      return this;
    }

    public Builder withImdbId(String imdbId) {
      this.imdbId = imdbId;
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

    public Builder withTvSpielfilmDetailUrl(String tvSpielfilmDetailUrl) {
      this.tvSpielfilmDetailUrl = tvSpielfilmDetailUrl;
      return this;
    }

    public Builder withOriginalTitleImdb(String originalTitleImdb) {
      this.originalTitleImdb = originalTitleImdb;
      return this;
    }

    public Builder withOriginalTitleTvSpielfilm(String originalTitleTvSpielfilm) {
      this.originalTitleTvSpielfilm = originalTitleTvSpielfilm;
      return this;
    }

    public Builder withDirectorImdb(String directorImdb) {
      this.directorImdb = directorImdb;
      return this;
    }

    public Builder withDirectorTvSpielfilm(String directorTvSpielfilm) {
      this.directorTvSpielfilm = directorTvSpielfilm;
      return this;
    }

    public Builder withTipp(boolean tipp) {
      this.tipp = tipp;
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

    public Builder withIsNew(boolean isNew) {
      this.isNew = isNew;
      return this;
    }

    public Builder withAwards(String awards) {
      this.awards = awards;
      return this;
    }

    public Builder withRecorded(boolean recorded) {
      this.recorded = recorded;
      return this;
    }

    public Builder withNoConflict(boolean noConflict) {
      this.noConflict = noConflict;
      return this;
    }

    public Builder withSuccess(boolean success) {
      this.success = success;
      return this;
    }

    public Builder withTvSpielfilmOverviewData(TvSpielfilmOverviewData tvSpielfilmOverviewData) {
      return withChannel(tvSpielfilmOverviewData.getChannel())
          .withCountry(tvSpielfilmOverviewData.getCountry())
          .withDate(tvSpielfilmOverviewData.getDate())
          .withGenre(tvSpielfilmOverviewData.getGenre())
          .withTime(tvSpielfilmOverviewData.getTime())
          .withTitle(tvSpielfilmOverviewData.getTitle())
          .withTvSpielfilmRating(tvSpielfilmOverviewData.getTvSpielfilmRating())
          .withYear(tvSpielfilmOverviewData.getYear())
          .withTvSpielfilmDetailUrl(tvSpielfilmOverviewData.getTvSpielfilmDetailUrl());
    }

    public Builder withTvSpielfilmDetailData(TvSpielfilmDetailData tvSpielfilmDetailData) {
      return withDirectorTvSpielfilm(tvSpielfilmDetailData.getDirector())
          .withOriginalTitleTvSpielfilm(tvSpielfilmDetailData.getOriginalTitle())
          .withImages(tvSpielfilmDetailData.getImages())
          .withDescription(tvSpielfilmDetailData.getDescription())
          .withTipp(tvSpielfilmDetailData.isTipp())
          .withIsNew(tvSpielfilmDetailData.isNew());
    }

    public Builder withImdbSuggestData(ImdbSuggestData imdbSuggestData) {
      return withImdbId(imdbSuggestData.getImdbId());
    }

    public Builder withImdbDetailData(ImdbDetailData imdbDetailData) {
      return withImdbRating(imdbDetailData.getImdbRating())
          .withMetacriticRating(imdbDetailData.getMetacriticRating())
          .withAwards(imdbDetailData.getAwards())
          .withStars(imdbDetailData.getStars())
          .withOriginalTitleImdb(imdbDetailData.getOriginalTitle())
          .withDirectorImdb(imdbDetailData.getDirector())
          .withNoConflict(false);
    }

    public MovieEntity build() {
      return new MovieEntity(this);
    }
  }

}
