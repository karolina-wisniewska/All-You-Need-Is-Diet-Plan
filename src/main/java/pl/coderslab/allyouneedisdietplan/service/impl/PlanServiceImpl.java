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
import pl.coderslab.allyouneedisdietplan.model.RecipeQuery;
import pl.coderslab.allyouneedisdietplan.model.RecipeResource;
import pl.coderslab.allyouneedisdietplan.model.RecipeResourceList;
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
    if(plan == null) {
      plan = new Plan();
    }
    plan.setUser(currentUser);
    save(plan);

    UserDetails currentUserDetails = userDetailsService.findByUser(currentUser);
    List<MealType> mealTypes = mealTypeService.findAll();
    List<List<DietPlanItem>> resultItems = new ArrayList<>();

    for (Integer j = 0; j < mealTypes.size(); j++) {
      MealType mealType = mealTypes.get(j);
      String url = getUserUrl(mealType, currentUserDetails);
      RestTemplate restTemplate = new RestTemplate();
      RecipeResourceList response = restTemplate.getForObject(url, RecipeResourceList.class);
      List<RecipeResource> recipes = response.getHits();

      if(recipes.isEmpty()){
        return resultItems;
      }
      List<DietPlanItem> dietPlanItems = dietPlanItemService.findByPlanAndMealTypeOrderByIdAsc(plan, mealType);

      List<DietPlanItem> resultItemsPerMeal = new ArrayList<>();
      for (Integer i = 0; i <= 6; i++) {
        Recipe recipe = new Recipe();
        recipe.setLabel(recipes.get(i).getRecipe().getLabel());
        recipe.setExternalLink(recipes.get(i).get_links().getSelf().getHref());
        Recipe finalRecipe = recipeService.save(recipe);

        DayName dayName = dayNameService.findById(i + 1);

        DietPlanItem dietPlanItem = new DietPlanItem();
        if(!dietPlanItems.isEmpty()){
          dietPlanItem = dietPlanItemService.findByPlanAndMealTypeOrderByIdAsc(plan, mealType).get(i);
        }

        dietPlanItem.setPlan(plan);
        dietPlanItem.setMealType(mealType);
        dietPlanItem.setRecipe(finalRecipe);
        dietPlanItem.setDayName(dayName);
        dietPlanItemService.save(dietPlanItem);

        resultItemsPerMeal.add(dietPlanItem);
      }
      resultItems.add(resultItemsPerMeal);
    }
    return resultItems;
  }

  @Override
  public List<List<DietPlanItem>> loadDietPlanItemsForPlan(User currentUser) {
    Plan plan = findByUser(currentUser);

    List<MealType> mealTypes = mealTypeService.findAll();
    List<List<DietPlanItem>> resultItems = new ArrayList<>();

    for (Integer j = 0; j < mealTypes.size(); j++) {
      List<DietPlanItem> dietPlanItems = dietPlanItemService.findByPlanAndMealTypeOrderByIdAsc(plan, mealTypes.get(j));
      List<DietPlanItem> resultItemsPerMeal = new ArrayList<>();

      for (Integer i = 0; i <= 6; i++) {
        DietPlanItem dietPlanItem = dietPlanItems.get(i);
        resultItemsPerMeal.add(dietPlanItem);
      }
      resultItems.add(resultItemsPerMeal);
    }
    return resultItems;
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
  public String getSingleUrl(RecipeQuery recipeQuery, UserDetails userDetails) {
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
