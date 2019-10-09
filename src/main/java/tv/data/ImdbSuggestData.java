package tv.data;

public class ImdbSuggestData {

  private final String imdbId;

  private ImdbSuggestData(Builder builder) {
    this.imdbId = builder.imdbId;
  }

  public String getImdbId() {
    return imdbId;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String imdbId;

    private Builder() {
    }

    public Builder withImdbId(String imdbId) {
      this.imdbId = imdbId;
      return this;
    }

    public ImdbSuggestData build() {
      return new ImdbSuggestData(this);
    }
  }

}
