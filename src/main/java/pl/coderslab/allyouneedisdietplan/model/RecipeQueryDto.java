package pl.coderslab.allyouneedisdietplan.model;

import lombok.Data;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.UrlElement;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.CuisineType;
import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.DishType;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.MealType;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeQueryDto {

  private DietPlanItem dietPlanItem;
  private MealType mealType;
  private DishType dishType;
  private CuisineType cuisineType;
  private Query query;

  public List<UrlElement> getQueryUrlElements(){
    List<UrlElement> urlElements = new ArrayList<>();
    if(cuisineType != null){
      urlElements.add(cuisineType);
    }
    if(mealType != null){
      urlElements.add(mealType);
    }
    if(dishType != null){
      urlElements.add(dishType);
    }
    if(query != null){
      urlElements.add(query);
    }
    return urlElements;
  }
}
