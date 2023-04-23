package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.MealType;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.RecipeQuery;

import java.util.List;

public interface PlanService {

  void save(Plan plan);
  Plan findByUser(User user);
  String getUserUrl(MealType mealType, UserDetails userDetails);
  String getSingleUrl(RecipeQuery recipeQuery, UserDetails userDetails);
  Long getMealCalories(MealType mealType, UserDetails userDetails);
  List<List<DietPlanItem>> getDietPlanItemsForPlan(User user);
  List<List<DietPlanItem>> loadDietPlanItemsForPlan(User user);

}
