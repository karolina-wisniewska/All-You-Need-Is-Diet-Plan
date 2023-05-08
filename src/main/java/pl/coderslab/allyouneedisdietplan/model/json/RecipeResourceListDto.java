package pl.coderslab.allyouneedisdietplan.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RecipeResourceListDto {
  private List<RecipeResourceDto> hits;
  public RecipeResourceListDto(){
    hits = new ArrayList<>();

  }
}
