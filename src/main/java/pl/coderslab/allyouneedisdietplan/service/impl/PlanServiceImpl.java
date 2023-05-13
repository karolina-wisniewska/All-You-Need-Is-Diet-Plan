package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.DayName;
import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.MealType;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.entity.Recipe;
import pl.coderslab.allyouneedisdietplan.entity.UserParams;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.RecipeQueryDto;
import pl.coderslab.allyouneedisdietplan.external.edamam.RecipeResourceDto;
import pl.coderslab.allyouneedisdietplan.repository.PlanRepository;
import pl.coderslab.allyouneedisdietplan.service.dictionary.DayNameService;
import pl.coderslab.allyouneedisdietplan.service.DietPlanItemService;
import pl.coderslab.allyouneedisdietplan.service.dictionary.MealTypeService;
import pl.coderslab.allyouneedisdietplan.service.PlanService;
import pl.coderslab.allyouneedisdietplan.service.external.EdamamService;
import pl.coderslab.allyouneedisdietplan.service.RecipeService;
import pl.coderslab.allyouneedisdietplan.service.UserParamsService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

  private final PlanRepository planRepository;
  private final UserParamsService userParamsService;
  private final MealTypeService mealTypeService;
  private final DietPlanItemService dietPlanItemService;
  private final RecipeService recipeService;
  private final DayNameService dayNameService;
  private final EdamamService edamamService;
  @Override
  public void save(Plan plan) {
    planRepository.save(plan);
  }

  @Override
  public Plan findByUser(User user) {
    return planRepository.findByUser(user);
  }

  @Override
  public List<List<DietPlanItem>> getDietPlanItemsForPlan(User currentUser) {
    Plan plan = findByUser(currentUser);
    if (plan == null) {
      plan = new Plan();
      plan.setUser(currentUser);
      save(plan);
    }

    List<MealType> mealTypes = mealTypeService.findAll();
    List<List<DietPlanItem>> allResultItems = new ArrayList<>();

    for (MealType mealType : mealTypes) {
      List<RecipeResourceDto> recipeResources = getRecipesPerMealType(mealType, currentUser);
      if (recipeResources.size() < dayNameService.count()) {
        return new ArrayList<>();
      }

      List<DietPlanItem> dietPlanItems = dietPlanItemService.findByPlanAndMealTypeOrderByIdAsc(plan, mealType);
      List<DietPlanItem> resultItemsPerMeal = new ArrayList<>();

      for (int i = 0; i < dayNameService.count(); i++) {
        Recipe recipe = recipeService.createFromResourceListByPosition(i, recipeResources);
        recipe = recipeService.save(recipe);

        DayName dayName = dayNameService.findById(i + 1);

        DietPlanItem dietPlanItem = new DietPlanItem();
        if (!dietPlanItems.isEmpty()) {
          dietPlanItem = dietPlanItems.get(i);
        }
        dietPlanItem.setPlan(plan);
        dietPlanItem.setMealType(mealType);
        dietPlanItem.setRecipe(recipe);
        dietPlanItem.setDayName(dayName);
        dietPlanItemService.save(dietPlanItem);

        resultItemsPerMeal.add(dietPlanItem);
      }
      allResultItems.add(resultItemsPerMeal);
    }
    return allResultItems;
  }

  @Override
  public List<List<DietPlanItem>> loadDietPlanItemsForPlan(User currentUser) {
    Plan plan = findByUser(currentUser);

    List<MealType> mealTypes = mealTypeService.findAll();
    List<List<DietPlanItem>> allResultItems = new ArrayList<>();

    for (MealType mealType : mealTypes) {
      List<DietPlanItem> dietPlanItemsPerMeal = dietPlanItemService.findByPlanAndMealTypeOrderByIdAsc(plan, mealType);
      List<DietPlanItem> resultItemsPerMeal = new ArrayList<>();

      for (int i = 0; i < dayNameService.count(); i++) {
        DietPlanItem dietPlanItem = dietPlanItemsPerMeal.get(i);
        resultItemsPerMeal.add(dietPlanItem);
      }
      allResultItems.add(resultItemsPerMeal);
    }
    return allResultItems;
  }

  @Override
  public List<RecipeResourceDto> getRecipesPerMealType(MealType mealType, User user) {
    UserParams currentUserParams = userParamsService.findByUser(user);
    String url = edamamService.getUserUrl(mealType, currentUserParams);
    return edamamService.getRecipeResourcesFromApi(url);
  }

  @Override
  public void replaceRecipeInDietPlanItem(List<RecipeResourceDto> recipeResources, DietPlanItem itemToEdit) {
    Recipe recipe = recipeService.createFromResourceListByPosition(0, recipeResources);
    recipe = recipeService.save(recipe);
    itemToEdit.setRecipe(recipe);
    dietPlanItemService.save(itemToEdit);
  }

  @Override
  public boolean isPlanComplete(Plan plan) {
    return dietPlanItemService.countByPlan(plan) == (dayNameService.count() * mealTypeService.count());
  }

  @Override
  public List<RecipeResourceDto> getRecipesForRecipeQuery(RecipeQueryDto recipeQuery, User user) {
    UserParams currentUserParams = userParamsService.findByUser(user);
    String url = edamamService.getSingleUrl(recipeQuery, currentUserParams);
    return edamamService.getRecipeResourcesFromApi(url);
  }
}
