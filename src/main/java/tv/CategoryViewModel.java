package tv;

import java.util.Collections;
import java.util.List;

public class CategoryViewModel {

  private final String categoryName;
  private final List<MovieViewModel> movies;

  private CategoryViewModel(Builder builder) {
    this.categoryName = builder.categoryName;
    this.movies = builder.movies;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public List<MovieViewModel> getMovies() {
    return movies;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String categoryName;
    private List<MovieViewModel> movies = Collections.emptyList();

    private Builder() {
    }

    public Builder withCategoryName(String categoryName) {
      this.categoryName = categoryName;
      return this;
    }

    public Builder withMovies(List<MovieViewModel> movies) {
      this.movies = movies;
      return this;
    }

    public CategoryViewModel build() {
      return new CategoryViewModel(this);
    }
  }

}
