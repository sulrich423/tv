package tv;

import java.util.Collections;
import java.util.List;

public class TvSpielfilmDetailData {

  private final String originalTitle;
  private final String director;
  private final List<String> images;
  private final String description;
  private final boolean tipp;
  private final boolean isNew;

  private TvSpielfilmDetailData(Builder builder) {
    this.originalTitle = builder.originalTitle;
    this.director = builder.director;
    this.images = builder.images;
    this.description = builder.description;
    this.tipp = builder.tipp;
    this.isNew = builder.isNew;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public String getDirector() {
    return director;
  }

  public List<String> getImages() {
    return images;
  }

  public boolean isTipp() {
    return tipp;
  }

  public String getDescription() {
    return description;
  }

  public boolean isNew() {
    return isNew;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String originalTitle;
    private String director;
    private List<String> images = Collections.emptyList();
    private String description;
    private boolean tipp;
    private boolean isNew;

    private Builder() {
    }

    public Builder withOriginalTitle(String originalTitle) {
      this.originalTitle = originalTitle;
      return this;
    }

    public Builder withDirector(String director) {
      this.director = director;
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

    public Builder withTipp(boolean tipp) {
      this.tipp = tipp;
      return this;
    }

    public Builder withIsNew(boolean isNew) {
      this.isNew = isNew;
      return this;
    }

    public TvSpielfilmDetailData build() {
      return new TvSpielfilmDetailData(this);
    }
  }

}
