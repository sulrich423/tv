package tv.view;

import java.time.LocalDateTime;

public class AiringData {

  private final Integer id;
  private final String channel;
  private final String date;
  private final String time;
  private final LocalDateTime start;
  private final LocalDateTime end;
  private final boolean canRecord;
  private final boolean recorded;

  private AiringData(Builder builder) {
    this.id = builder.id;
    this.channel = builder.channel;
    this.date = builder.date;
    this.time = builder.time;
    this.start = builder.start;
    this.end = builder.end;
    this.canRecord = builder.canRecord;
    this.recorded = builder.recorded;
  }

  public Integer getId() {
    return id;
  }

  public boolean isRecorded() {
    return recorded;
  }

  public boolean isCanRecord() {
    return canRecord;
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

  public LocalDateTime getStart() {
    return start;
  }

  public LocalDateTime getEnd() {
    return end;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private Integer id;
    private String channel;
    private String date;
    private String time;
    private LocalDateTime start;
    private LocalDateTime end;
    private boolean canRecord;
    private boolean recorded;

    private Builder() {
    }

    public Builder withId(Integer id) {
      this.id = id;
      return this;
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

    public Builder withStart(LocalDateTime start) {
      this.start = start;
      return this;
    }

    public Builder withEnd(LocalDateTime end) {
      this.end = end;
      return this;
    }

    public Builder withCanRecord(boolean canRecord) {
      this.canRecord = canRecord;
      return this;
    }

    public Builder withRecorded(boolean recorded) {
      this.recorded = recorded;
      return this;
    }

    public AiringData build() {
      return new AiringData(this);
    }
  }
}
