package tv;

public class ImdbDetailData {

  private final String imdbRating;
  private final String metacriticRating;
  private final String imdbDirector;

  private ImdbDetailData(Builder builder) {
    this.imdbRating = builder.imdbRating;
    this.metacriticRating = builder.metacriticRating;
    this.imdbDirector = builder.imdbDirector;
  }

  public String getImdbRating() {
    return imdbRating;
  }

  public String getMetacriticRating() {
    return metacriticRating;
  }

  public String getImdbDirector() {
    return imdbDirector;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String imdbRating;
    private String metacriticRating;
    private String imdbDirector;

    private Builder() {
    }

    public Builder withImdbRating(String imdbRating) {
      this.imdbRating = imdbRating;
      return this;
    }

    public Builder withMetacriticRating(String metacriticRating) {
      this.metacriticRating = metacriticRating;
      return this;
    }

    public Builder withImdbDirector(String imdbDirector) {
      this.imdbDirector = imdbDirector;
      return this;
    }

    public ImdbDetailData build() {
      return new ImdbDetailData(this);
    }
  }

}
