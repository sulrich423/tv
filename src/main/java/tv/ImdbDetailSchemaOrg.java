package tv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImdbDetailSchemaOrg {

  private List<Actor> actor = Lists.newArrayList();

  public List<Actor> getActor() {
    return actor;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Actor {

    private String name;

    public String getName() {
      return name;
    }

  }
}