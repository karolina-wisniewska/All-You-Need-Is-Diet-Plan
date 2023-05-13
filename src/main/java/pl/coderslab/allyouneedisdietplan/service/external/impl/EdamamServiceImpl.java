package pl.coderslab.allyouneedisdietplan.service.external.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Diet;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Health;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.MealType;
import pl.coderslab.allyouneedisdietplan.entity.UserParams;
import pl.coderslab.allyouneedisdietplan.model.RecipeQueryDto;
import pl.coderslab.allyouneedisdietplan.external.edamam.RecipeResourceDto;
import pl.coderslab.allyouneedisdietplan.external.edamam.RecipeResourceListDto;
import pl.coderslab.allyouneedisdietplan.service.external.EdamamService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EdamamServiceImpl implements EdamamService {

  private final String API_URL_PART = "https://api.edamam.com/api/recipes/v2?type=public";
  private final String APP_KEY_PART = "&app_id=40fa347c&app_key=0eb977d62e50265cf4df0451172393a6";
  private final String RANDOM_PART = "&random=true";
  private final String RESULT_FIELDS_PART = "&field=label&field=shareAs&field=externalId";

  @Override
  public String getUrlToShowRecipeDetails(String url) {
    RestTemplate restTemplate = new RestTemplate();
    RecipeResourceDto recipeResource = restTemplate.getForObject(url, RecipeResourceDto.class);
    return recipeResource.getRecipe().getShareAs();
  }

  @Override
  public List<RecipeResourceDto> getRecipeResourcesFromApi(String url) {
    RestTemplate restTemplate = new RestTemplate();
    RecipeResourceListDto response = restTemplate.getForObject(url, RecipeResourceListDto.class);
    return response.getHits();
  }

  @Override
  public String getUserUrl(MealType mealType, UserParams userParams) {
    String url = API_URL_PART + APP_KEY_PART + RANDOM_PART + RESULT_FIELDS_PART;
    url += "&mealType=" + mealType.getName();
    if (userParams.getCuisineType() != null) {
      url += "&cuisineType=" + userParams.getCuisineType().getName();
    }
    List<Health> healths = userParams.getHealths();
    if (healths != null) {
      for (Health health : healths) {
        url += "&health=" + health.getName();
      }
    }
    List<Diet> diets = userParams.getDiets();
    if (diets != null) {
      for (Diet diet : diets) {
        url += "&diet=" + diet.getName();
      }
    }
    Long calories = getMealCalories(mealType, userParams);
    double precision = 0.05;
    url += "&calories=" + (calories * (1 - precision)) + "-" + (calories * (1 + precision));
    return url;
  }

  @Override
  public String getSingleUrl(RecipeQueryDto recipeQuery, UserParams userParams) {
    String url = API_URL_PART + APP_KEY_PART + RANDOM_PART + RESULT_FIELDS_PART;
    if (recipeQuery.getMealType() != null) {
      url += "&mealType=" + recipeQuery.getMealType().getName();
      Long calories = getMealCalories(recipeQuery.getMealType(), userParams);
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
    List<Health> healths = userParams.getHealths();
    if (healths != null) {
      for (Health health : healths) {
        url += "&health=" + health.getName();
      }
    }
    List<Diet> diets = userParams.getDiets();
    if (diets != null) {
      for (Diet diet : diets) {
        url += "&diet=" + diet.getName();
      }
    }
    return url;
  }

  private Long getMealCalories(MealType mealType, UserParams userParams) {
    return Math.round(userParams.getDailyCalories() * mealType.getFraction());
  }
}
