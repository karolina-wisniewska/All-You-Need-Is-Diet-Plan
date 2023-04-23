package pl.coderslab.allyouneedisdietplan.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Recipe {
  private String label;
  private String shareAs;

}
