package pl.coderslab.allyouneedisdietplan.external.edamam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RecipeResourceDto {

  private RecipeDto recipe;

  @JsonProperty("_links")
  private LinkDto links;

}
