package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.MealType;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.RecipeQuery;
import pl.coderslab.allyouneedisdietplan.model.json.RecipeResource;

import java.util.List;

public interface PlanService {

  void save(Plan plan);
  Plan findByUser(User user);

  String getUserUrl(MealType mealType, UserDetails userDetails);
  String getSingleUrl(RecipeQuery recipeQuery, UserDetails userDetails);
  String getUrlToShowRecipeDetails(String url);
  Long getMealCalories(MealType mealType, UserDetails userDetails);

  List<List<DietPlanItem>> getDietPlanItemsForPlan(User user);
  List<List<DietPlanItem>> loadDietPlanItemsForPlan(User user);

  List<RecipeResource> getRecipesPerMealType(MealType mealType, User user);
  List<RecipeResource> getRecipeResourcesFromApi(String url);
  List<RecipeResource> getRecipesForRecipeQuery(RecipeQuery recipeQuery, User user);

  void replaceRecipeInDietPlanItem(List<RecipeResource> recipes, DietPlanItem itemToEdit);
}
