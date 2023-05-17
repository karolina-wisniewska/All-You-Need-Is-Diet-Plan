package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.MealType;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.external.edamam.RecipeResourceDto;
import pl.coderslab.allyouneedisdietplan.model.RecipeQueryDto;

import java.util.List;
import java.util.Optional;

public interface PlanService {

  Plan createNewUserPlan(User currentUser);
  void save(Plan plan);
  Optional<Plan> findByUser(User user);
  List<List<DietPlanItem>> getDietPlanItemsForPlan(User user);
  List<List<DietPlanItem>> loadDietPlanItemsForPlan(User user);
  List<RecipeResourceDto> getRecipesPerMealType(MealType mealType, User user);
  List<RecipeResourceDto> getRecipesForRecipeQuery(RecipeQueryDto recipeQuery, User user);
  void replaceRecipeInDietPlanItem(List<RecipeResourceDto> recipes, DietPlanItem itemToEdit);
  boolean isPlanComplete(Plan plan);


}
