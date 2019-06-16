package tv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAllServicesResponse {

  private List<Service> services;

  public List<Service> getServices() {
    return services;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Service {

    private List<SubService> subservices;

    public List<SubService> getSubservices() {
      return subservices;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SubService {

      private String servicereference;
      private String program;
      private String servicename;
      private int pos;

      public String getServicereference() {
        return servicereference;
      }

      public String getProgram() {
        return program;
      }

      public String getServicename() {
        return servicename;
      }

      public int getPos() {
        return pos;
      }

    }
  }
}
