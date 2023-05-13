package pl.coderslab.allyouneedisdietplan.external.edamam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RecipeDto {
  private String label;
  private String shareAs;

}
