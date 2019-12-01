package tv.openwebif;

import java.net.URI;
import java.time.ZoneId;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.uri.internal.JerseyUriBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.collect.ImmutableMap;

import tv.db.MovieEntity;
import tv.view.AiringData;
import tv.view.MovieConverter;

@Component
public class OpenWebifService {

  private static final String BASE_URL = "http://192.168.178.26/api/";

  public static final ImmutableMap<String, String> TVS_TO_RECORD_CHANNEL = ImmutableMap.<String, String>builder()
      .put("ARD", "Das Erste HD")
      .put("ZDF", "ZDF HD")
      .put("RTL", "RTL Television")
      .put("SAT1", "SAT.1")
      .put("PRO7", "ProSieben")
      .put("RTL2", "RTLZWEI ")
      .put("VOX", "VOX")
      .put("K1", "kabel eins")
      .put("ARTE", "arte HD")
      .put("SERVU", "ServusTV HD Deutschland")
      .put("SIXX", "SIXX")
      .put("3SAT", "3sat HD")
      .put("N3", "NDR FS HH HD")
      .put("WDR", "WDR HD Dortmund")
      .put("HR", "hr-fernsehen")
      .put("MDR", "MDR Thüringen")
      .put("RBB", "rbb Berlin")
      .put("SWR", "SWR BW HD")
      .put("BR", "BR Fernsehen Nord HD")
      .put("TELE5", "TELE 5")
      .put("PRO7M", "Pro7 MAXX")
      .put("2NEO", "zdf_neo HD")
      .put("SUPER", "SUPER RTL")
      .put("RTL-N", "NITRO")
      .put("FES", "ONE")
      .put("ALPHA", "ARD-alpha")
      .put("DISNE", "Disney Channel")
      .put("SAT1G", "SAT.1 Gold")
      .put("RTLPL", "RTLplus")
      .put("TOGGO", "TOGGO plus")
      .put("EOTV", "eoTV")
      .build();

  @Inject
  private MovieConverter movieConverter;

  public boolean timerAdd(MovieEntity entity) {
    return timerChange(entity, TimerUrl.ADD);
  }

  public boolean timerDelete(MovieEntity entity) {
    return timerChange(entity, TimerUrl.DELETE);
  }

  private boolean timerChange(MovieEntity entity, TimerUrl timerUrl) {
    try {
      String responseString = ClientBuilder.newBuilder()
          .connectTimeout(5, TimeUnit.SECONDS)
          .readTimeout(5, TimeUnit.SECONDS)
          .build()
          .register(JacksonJsonProvider.class)
          .target(createTimerUrl(entity, timerUrl))
          .request()
          .buildGet()
          .invoke(String.class);

      TimerResponse response = new ObjectMapper().readValue(responseString, TimerResponse.class);

      return response.isSuccess();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private URI createTimerUrl(MovieEntity entity, TimerUrl timerUrl) throws Exception {
    AiringData airingData = movieConverter.getAiringData(entity);
    String title = entity.getTitle();
    String channel = airingData.getChannel();
    long begin = airingData.getStart().minusMinutes(10)
        .toEpochSecond(ZoneId.systemDefault().getRules().getOffset(airingData.getStart()));
    long end = airingData.getEnd().plusMinutes(30)
        .toEpochSecond(ZoneId.systemDefault().getRules().getOffset(airingData.getEnd()));

    String serviceReference = getServiceReferenceFromChannel(channel);

    return new JerseyUriBuilder()
        .path(BASE_URL + timerUrl.getUrl())
        .queryParam("description", "")
        .queryParam("disabled", "0")
        .queryParam("afterevent", "3")
        .queryParam("tags", "")
        .queryParam("repeated", "0")
        .queryParam("justplay", "0")
        .queryParam("name", title)
        .queryParam("sRef", serviceReference)
        .queryParam("begin", begin)
        .queryParam("end", end)
        .build();
  }

  private String getServiceReferenceFromChannel(String entityChannel) throws Exception {

    String responseString = ClientBuilder.newBuilder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .build()
        .register(JacksonJsonProvider.class)
        .target(BASE_URL + "getallservices")
        .request()
        .buildGet()
        .invoke(String.class);

    GetAllServicesResponse response = new ObjectMapper().readValue(responseString, GetAllServicesResponse.class);

    String channel = TVS_TO_RECORD_CHANNEL.get(entityChannel);

    Optional<String> serviceReference = response.getServices().stream()
        .flatMap(service -> service.getSubservices().stream())
        .filter(subservice -> subservice.getServicename().equals(channel))
        .findFirst()
        .map(subservice -> subservice.getServicereference());

    return serviceReference.orElse(null);
  }

  private enum TimerUrl {
    ADD("timeradd"),
    DELETE("timerdelete");

    private final String url;

    private TimerUrl(String url) {
      this.url = url;
    }

    public String getUrl() {
      return url;
    }

  }

}
