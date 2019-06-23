package tv.data;

public class ImdbSuggestData {

  private final String imdbId;
  private final String stars;

  private ImdbSuggestData(Builder builder) {
    this.imdbId = builder.imdbId;
    this.stars = builder.stars;
  }

  public String getImdbId() {
    return imdbId;
  }

  public String getStars() {
    return stars;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String imdbId;
    private String stars;

    private Builder() {
    }

    public Builder withImdbId(String imdbId) {
      this.imdbId = imdbId;
      return this;
    }

    public Builder withStars(String stars) {
      this.stars = stars;
      return this;
    }

    public ImdbSuggestData build() {
      return new ImdbSuggestData(this);
    }
  }

}
