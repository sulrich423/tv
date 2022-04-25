package tv.data;

public class ImdbDetailData {

  private final String imdbRating;
  private final String metacriticRating;
  private final String awards;
  private final String director;
  private final String stars;
  private final String originalTitle;

  private ImdbDetailData(Builder builder) {
    this.imdbRating = builder.imdbRating;
    this.metacriticRating = builder.metacriticRating;
    this.awards = builder.awards;
    this.director = builder.director;
    this.stars = builder.stars;
    this.originalTitle = builder.originalTitle;
  }

  public String getImdbRating() {
    return imdbRating;
  }

  public String getMetacriticRating() {
    return metacriticRating;
  }

  public String getAwards() {
    return awards;
  }

  public String getDirector() {
    return director;
  }

  public String getStars() {
    return stars;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static Builder but(ImdbDetailData imdbDetailData) {
    return new Builder(imdbDetailData);
  }

  public static final class Builder {
    private String imdbRating;
    private String metacriticRating;
    private String awards;
    private String director;
    private String stars;
    private String originalTitle;

    private Builder() {
    }

    private Builder(ImdbDetailData imdbDetailData) {
      this.imdbRating = imdbDetailData.imdbRating;
      this.metacriticRating = imdbDetailData.metacriticRating;
      this.awards = imdbDetailData.awards;
      this.director = imdbDetailData.director;
      this.stars = imdbDetailData.stars;
      this.originalTitle = imdbDetailData.originalTitle;
    }

    public Builder withImdbRating(String imdbRating) {
      this.imdbRating = imdbRating;
      return this;
    }

    public Builder withMetacriticRating(String metacriticRating) {
      this.metacriticRating = metacriticRating;
      return this;
    }

    public Builder withAwards(String awards) {
      this.awards = awards;
      return this;
    }

    public Builder withDirector(String director) {
      this.director = director;
      return this;
    }

    public Builder withStars(String stars) {
      this.stars = stars;
      return this;
    }

    public Builder withOriginalTitle(String originalTitle) {
      this.originalTitle = originalTitle;
      return this;
    }

    public ImdbDetailData build() {
      return new ImdbDetailData(this);
    }
  }

}
