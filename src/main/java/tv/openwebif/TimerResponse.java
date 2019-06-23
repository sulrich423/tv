package tv.openwebif;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimerResponse {

  private String message;

  @JsonProperty("result")
  private boolean success;

  public String getMessage() {
    return message;
  }

  public boolean isSuccess() {
    return success;
  }

}
