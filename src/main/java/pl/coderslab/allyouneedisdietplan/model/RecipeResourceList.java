package pl.coderslab.allyouneedisdietplan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RecipeResourceList {
  private List<RecipeResource> hits;
  public  RecipeResourceList(){
    hits = new ArrayList<>();

  }
}
