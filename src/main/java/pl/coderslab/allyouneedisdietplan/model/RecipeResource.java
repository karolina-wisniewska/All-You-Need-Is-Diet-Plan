package pl.coderslab.allyouneedisdietplan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RecipeResource {

  private Recipe recipe;
  private Link _links;

}
