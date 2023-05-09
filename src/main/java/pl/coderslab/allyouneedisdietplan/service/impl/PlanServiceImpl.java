package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.DayName;
import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.MealType;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.entity.Recipe;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.RecipeQueryDto;
import pl.coderslab.allyouneedisdietplan.model.json.RecipeResourceDto;
import pl.coderslab.allyouneedisdietplan.repository.PlanRepository;
import pl.coderslab.allyouneedisdietplan.service.DayNameService;
import pl.coderslab.allyouneedisdietplan.service.DietPlanItemService;
import pl.coderslab.allyouneedisdietplan.service.MealTypeService;
import pl.coderslab.allyouneedisdietplan.service.PlanService;
import pl.coderslab.allyouneedisdietplan.service.ProviderService;
import pl.coderslab.allyouneedisdietplan.service.RecipeService;
import pl.coderslab.allyouneedisdietplan.service.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

  private final PlanRepository planRepository;
  private final UserDetailsService userDetailsService;
  private final MealTypeService mealTypeService;
  private final DietPlanItemService dietPlanItemService;
  private final RecipeService recipeService;
  private final DayNameService dayNameService;
  private final ProviderService providerService;
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
    UserDetails currentUserDetails = userDetailsService.findByUser(user);
    String url = providerService.getUserUrl(mealType, currentUserDetails);
    return providerService.getRecipeResourcesFromApi(url);
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
    UserDetails currentUserDetails = userDetailsService.findByUser(user);
    String url = providerService.getSingleUrl(recipeQuery, currentUserDetails);
    return providerService.getRecipeResourcesFromApi(url);
  }
}
