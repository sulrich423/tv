package tv.websocket;

public class WebSocketMessage {

  private String value;

  public WebSocketMessage(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
