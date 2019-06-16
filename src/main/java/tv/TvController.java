package tv;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TvController {

  @Inject
  private TvComponent tvComponent;

  @GetMapping("/")
  public ModelAndView display(@RequestParam(name = "date", required = false) String dateParam) {
    String date = Optional.ofNullable(dateParam).orElse(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
    LocalDate today = LocalDate.from(DateTimeFormatter.ISO_DATE.parse(date));
    List<CategoryViewModel> sortedMovies = tvComponent.getMovies(date);

    Map<String, Object> modelMap = new HashMap<>();

    modelMap.put("categories", sortedMovies);
    modelMap.put("today", today.format(DateTimeFormatter.ISO_DATE));
    modelMap.put("todayFormatted", today.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    modelMap.put("tomorrow", today.plusDays(1).format(DateTimeFormatter.ISO_DATE));
    modelMap.put("yesterday", today.minusDays(1).format(DateTimeFormatter.ISO_DATE));
    return new ModelAndView("categories", modelMap);
  }

  // @GetMapping("/update/")
  @MessageMapping("/update")
  @SendTo("/topic/movies")
  // public SuccessResult update(@RequestParam String date, DateMessage dateMessage) {
  public WebSocketMessage update(DateMessage dateMessage) {
    String date = dateMessage.getDate();
    tvComponent.delete(date);
    tvComponent.update(date);

    return new WebSocketMessage("done");
  }

  @PostMapping("/toggleRecord/")
  @ResponseBody
  public SuccessResult toggleRecord(@RequestParam Integer id) {
    boolean success = tvComponent.toggleRecord(id);

    return new SuccessResult(success);
  }

}
