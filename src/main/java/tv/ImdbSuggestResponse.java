package tv;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImdbSuggestResponse {

  public static final List<String> RELEVANT_KINDS = ImmutableList.of("feature", "video", "TV movie");

  @JsonProperty("d")
  private List<Entry> list = new ArrayList<>();

  public List<Entry> getList() {
    return list;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Entry {

    private String id;

    @JsonProperty("s")
    private String stars;

    @JsonProperty("y")
    private Integer year;

    @JsonProperty("q")
    private String kind;

    public String getId() {
      return id;
    }

    public String getStars() {
      return stars;
    }

    public Integer getYear() {
      return year;
    }

    public String getKind() {
      return kind;
    }

  }
}
