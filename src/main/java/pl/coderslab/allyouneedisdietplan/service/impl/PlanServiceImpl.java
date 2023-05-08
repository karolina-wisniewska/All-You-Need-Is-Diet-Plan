package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.allyouneedisdietplan.entity.DayName;
import pl.coderslab.allyouneedisdietplan.entity.Diet;
import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.Health;
import pl.coderslab.allyouneedisdietplan.entity.MealType;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.entity.Recipe;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.RecipeQueryDto;
import pl.coderslab.allyouneedisdietplan.model.json.RecipeResourceDto;
import pl.coderslab.allyouneedisdietplan.model.json.RecipeResourceListDto;
import pl.coderslab.allyouneedisdietplan.repository.PlanRepository;
import pl.coderslab.allyouneedisdietplan.service.DayNameService;
import pl.coderslab.allyouneedisdietplan.service.DietPlanItemService;
import pl.coderslab.allyouneedisdietplan.service.MealTypeService;
import pl.coderslab.allyouneedisdietplan.service.PlanService;
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
  private final String API_URL_PART = "https://api.edamam.com/api/recipes/v2?type=public";
  private final String APP_KEY_PART = "&app_id=40fa347c&app_key=0eb977d62e50265cf4df0451172393a6";
  private final String RANDOM_PART = "&random=true";
  private final String RESULT_FIELDS_PART = "&field=label&field=shareAs&field=externalId";

  @Override
  public void save(Plan plan) {
    planRepository.save(plan);
  }

  @Override
  public Plan findByUser(User user) {
    return planRepository.findByUser(user);
  }

  @Override
  public Long getMealCalories(MealType mealType, UserDetails userDetails) {
    return Math.round(userDetails.getDailyCalories() * mealType.getFraction());
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
  public String getUrlToShowRecipeDetails(String url) {
    RestTemplate restTemplate = new RestTemplate();
    RecipeResourceDto recipeResource = restTemplate.getForObject(url, RecipeResourceDto.class);
    return recipeResource.getRecipe().getShareAs();
  }

  @Override
  public List<RecipeResourceDto> getRecipesPerMealType(MealType mealType, User user) {
    UserDetails currentUserDetails = userDetailsService.findByUser(user);
    String url = getUserUrl(mealType, currentUserDetails);
    return getRecipeResourcesFromApi(url);
  }

  @Override
  public void replaceRecipeInDietPlanItem(List<RecipeResourceDto> recipeResources, DietPlanItem itemToEdit) {
    Recipe recipe = recipeService.createFromResourceListByPosition(0, recipeResources);
    recipe = recipeService.save(recipe);
    itemToEdit.setRecipe(recipe);
    dietPlanItemService.save(itemToEdit);
  }

  @Override
  public List<RecipeResourceDto> getRecipeResourcesFromApi(String url) {
    RestTemplate restTemplate = new RestTemplate();
    RecipeResourceListDto response = restTemplate.getForObject(url, RecipeResourceListDto.class);
    return response.getHits();
  }

  @Override
  public List<RecipeResourceDto> getRecipesForRecipeQuery(RecipeQueryDto recipeQuery, User user) {
    UserDetails currentUserDetails = userDetailsService.findByUser(user);
    String url = getSingleUrl(recipeQuery, currentUserDetails);
    return getRecipeResourcesFromApi(url);
  }

  @Override
  public String getUserUrl(MealType mealType, UserDetails userDetails) {
    String url = API_URL_PART + APP_KEY_PART + RANDOM_PART + RESULT_FIELDS_PART;
    url += "&mealType=" + mealType.getName();
    if (userDetails.getCuisineType() != null) {
      url += "&cuisineType=" + userDetails.getCuisineType().getName();
    }
    List<Health> healths = userDetails.getHealths();
    if (healths != null) {
      for (Health health : healths) {
        url += "&health=" + health.getName();
      }
    }
    List<Diet> diets = userDetails.getDiets();
    if (diets != null) {
      for (Diet diet : diets) {
        url += "&diet=" + diet.getName();
      }
    }
    Long calories = getMealCalories(mealType, userDetails);
    double precision = 0.05;
    url += "&calories=" + (calories * (1 - precision)) + "-" + (calories * (1 + precision));
    return url;
  }

  @Override
  public String getSingleUrl(RecipeQueryDto recipeQuery, UserDetails userDetails) {
    String url = API_URL_PART + APP_KEY_PART + RANDOM_PART + RESULT_FIELDS_PART;
    if (recipeQuery.getMealType() != null) {
      url += "&mealType=" + recipeQuery.getMealType().getName();
      Long calories = getMealCalories(recipeQuery.getMealType(), userDetails);
      double precision = 0.05;
      url += "&calories=" + (calories * (1 - precision)) + "-" + (calories * (1 + precision));
    }
    if (recipeQuery.getCuisineType() != null) {
      url += "&cuisineType=" + recipeQuery.getCuisineType().getName();
    }
    if (recipeQuery.getDishType() != null) {
      url += "&dishType=" + recipeQuery.getDishType().getName();
    }
    if (recipeQuery.getQuery() != null) {
      url += "&q=" + recipeQuery.getQuery();
    }
    List<Health> healths = userDetails.getHealths();
    if (healths != null) {
      for (Health health : healths) {
        url += "&health=" + health.getName();
      }
    }
    List<Diet> diets = userDetails.getDiets();
    if (diets != null) {
      for (Diet diet : diets) {
        url += "&diet=" + diet.getName();
      }
    }
    return url;
  }
}
