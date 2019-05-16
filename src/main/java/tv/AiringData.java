package tv;

import java.time.LocalDateTime;

public class AiringData {

  private final String channel;
  private final String date;
  private final String time;
  private final LocalDateTime localDateTime;

  private AiringData(Builder builder) {
    this.channel = builder.channel;
    this.date = builder.date;
    this.time = builder.time;
    this.localDateTime = builder.localDateTime;
  }

  public String getChannel() {
    return channel;
  }

  public String getDate() {
    return date;
  }

  public String getTime() {
    return time;
  }

  public LocalDateTime getLocalDateTime() {
    return localDateTime;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String channel;
    private String date;
    private String time;
    private LocalDateTime localDateTime;

    private Builder() {
    }

    public Builder withChannel(String channel) {
      this.channel = channel;
      return this;
    }

    public Builder withDate(String date) {
      this.date = date;
      return this;
    }

    public Builder withTime(String time) {
      this.time = time;
      return this;
    }

    public Builder withLocalDateTime(LocalDateTime localDateTime) {
      this.localDateTime = localDateTime;
      return this;
    }

    public AiringData build() {
      return new AiringData(this);
    }
  }
}
