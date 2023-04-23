package pl.coderslab.allyouneedisdietplan.model;

import lombok.Data;
import pl.coderslab.allyouneedisdietplan.entity.CuisineType;
import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.DishType;
import pl.coderslab.allyouneedisdietplan.entity.MealType;

@Data
public class RecipeQuery {

  private DietPlanItem dietPlanItem;
  private MealType mealType;
  private DishType dishType;
  private CuisineType cuisineType;
  private String query;
}
