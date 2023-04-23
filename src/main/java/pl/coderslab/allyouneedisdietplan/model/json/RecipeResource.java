package pl.coderslab.allyouneedisdietplan.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RecipeResource {

  private Recipe recipe;

  @JsonProperty("_links")
  private Link links;

}
