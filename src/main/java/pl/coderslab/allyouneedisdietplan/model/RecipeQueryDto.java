package pl.coderslab.allyouneedisdietplan.model;

import lombok.Data;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.CuisineType;
import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.DishType;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.MealType;

@Data
public class RecipeQueryDto {

  private DietPlanItem dietPlanItem;
  private MealType mealType;
  private DishType dishType;
  private CuisineType cuisineType;
  private String query;
}
