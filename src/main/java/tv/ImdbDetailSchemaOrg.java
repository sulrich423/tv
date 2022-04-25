package tv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImdbDetailSchemaOrg {

  private List<Person> actor = Lists.newArrayList();
  private Person director;
  private String name;
  private AggregateRating aggregateRating;

  public List<Person> getActor() {
    return actor;
  }

  public Person getDirector() {
    return director;
  }

  public String getName() {
    return name;
  }

  public AggregateRating getAggregateRating() {
    return aggregateRating;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Person {

    private String name;

    public String getName() {
      return name;
    }

  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class AggregateRating {

    private String ratingValue;

    public String getRatingValue() {
      return ratingValue;
    }

  }

}
