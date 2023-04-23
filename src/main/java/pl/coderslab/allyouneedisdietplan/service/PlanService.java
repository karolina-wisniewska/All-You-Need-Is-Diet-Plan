package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.MealType;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.RecipeQuery;

public interface PlanService {

  void save(Plan plan);
  Plan findByUser(User user);
  String getUserUrl(MealType mealType, UserDetails userDetails);
  String getSingleUrl(RecipeQuery recipeQuery, UserDetails userDetails);
  Long getMealCalories(MealType mealType, UserDetails userDetails);

}
