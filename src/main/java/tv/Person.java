package tv;

import java.util.Collections;
import java.util.List;

public class Person {

  private final String name;
  private final List<String> images;

  private Person(Builder builder) {
    this.name = builder.name;
    this.images = builder.images;
  }

  public String getName() {
    return name;
  }

  public List<String> getImages() {
    return images;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String name;
    private List<String> images = Collections.emptyList();

    private Builder() {
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withImages(List<String> images) {
      this.images = images;
      return this;
    }

    public Person build() {
      return new Person(this);
    }
  }
}
