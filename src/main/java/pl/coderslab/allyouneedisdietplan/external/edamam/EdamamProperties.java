package pl.coderslab.allyouneedisdietplan.external.edamam;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("edamam")
@PropertySource("classpath:application.yaml")
@Getter
@Setter
public class EdamamProperties {
  private String url;
  private List<Param> params = new ArrayList<>();
  private List<Field> fields = new ArrayList<>();
  private double precision;

  @Data
  public static class Param {
    private String name;
    private String value;
    public String getUrlPart(){
      return "&" + name + "=" + value;
    }
  }

  @Data
  public static class Field {
    private String value;
    public String getUrlPart(){
      return "&field=" + value;
    }
  }
}
