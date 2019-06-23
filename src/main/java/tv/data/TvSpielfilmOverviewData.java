package tv.data;

public class TvSpielfilmOverviewData {

  private final String title;
  private final String country;
  private final String year;
  private final String tvSpielfilmRating;
  private final String channel;
  private final String time;
  private final String date;
  private final String genre;
  private final String tvSpielfilmDetailUrl;

  private TvSpielfilmOverviewData(Builder builder) {
    this.title = builder.title;
    this.country = builder.country;
    this.year = builder.year;
    this.tvSpielfilmRating = builder.tvSpielfilmRating;
    this.channel = builder.channel;
    this.time = builder.time;
    this.date = builder.date;
    this.genre = builder.genre;
    this.tvSpielfilmDetailUrl = builder.tvSpielfilmDetailUrl;
  }

  public String getTitle() {
    return title;
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

  public String getChannel() {
    return channel;
  }

  public String getTime() {
    return time;
  }

  public String getDate() {
    return date;
  }

  public String getGenre() {
    return genre;
  }

  public String getTvSpielfilmDetailUrl() {
    return tvSpielfilmDetailUrl;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String title;
    private String country;
    private String year;
    private String tvSpielfilmRating;
    private String channel;
    private String time;
    private String date;
    private String genre;
    private String tvSpielfilmDetailUrl;

    private Builder() {
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

    public Builder withTvSpielfilmDetailUrl(String tvSpielfilmDetailUrl) {
      this.tvSpielfilmDetailUrl = tvSpielfilmDetailUrl;
      return this;
    }

    public TvSpielfilmOverviewData build() {
      return new TvSpielfilmOverviewData(this);
    }
  }

}
