package pl.coderslab.allyouneedisdietplan.model;

import lombok.Data;
import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.UrlElement;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.CuisineType;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.DishType;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.MealType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class RecipeQueryDto {

  private DietPlanItem dietPlanItem;
  private MealType mealType;
  private DishType dishType;
  private CuisineType cuisineType;
  private Query query;

  public List<UrlElement> getQueryUrlElements(){
    List<UrlElement> urlElements = new ArrayList<>();
    Optional.ofNullable(cuisineType).map(urlElements::add);
    Optional.ofNullable(mealType).map(urlElements::add);
    Optional.ofNullable(dishType).map(urlElements::add);
    Optional.ofNullable(query).map(urlElements::add);
    return urlElements;
  }
}
