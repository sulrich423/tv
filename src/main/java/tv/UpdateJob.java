package tv;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdateJob {

  @Inject
  private TvComponent tvComponent;

  @Scheduled(cron = "0 5 1 * * *")
  public void run() {
    LocalDate today = LocalDate.now();
    LocalDate tomorrow = LocalDate.now().plusDays(1);
    tvComponent.update(today.format(DateTimeFormatter.ISO_DATE));
    tvComponent.update(tomorrow.format(DateTimeFormatter.ISO_DATE));
  }

}
