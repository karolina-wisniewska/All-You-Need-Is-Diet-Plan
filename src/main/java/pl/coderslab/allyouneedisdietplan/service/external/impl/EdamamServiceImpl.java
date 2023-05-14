package pl.coderslab.allyouneedisdietplan.service.external.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.allyouneedisdietplan.entity.UserParams;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Diet;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Health;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.MealType;
import pl.coderslab.allyouneedisdietplan.external.edamam.EdamamProperties;
import pl.coderslab.allyouneedisdietplan.external.edamam.RecipeResourceDto;
import pl.coderslab.allyouneedisdietplan.external.edamam.RecipeResourceListDto;
import pl.coderslab.allyouneedisdietplan.model.RecipeQueryDto;
import pl.coderslab.allyouneedisdietplan.service.external.EdamamService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EdamamServiceImpl implements EdamamService {

  private final EdamamProperties edamamProperties;
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
    String url = getUrlFromProperties();
    url += "&mealType=" + mealType.getName();
    if (userParams.getCuisineType() != null) {
      url += userParams.getCuisineType().getUrlPart();
    }
    List<Health> healths = userParams.getHealths();
    if (healths != null) {
      for (Health health : healths) {
        url += health.getUrlPart();
      }
    }
    List<Diet> diets = userParams.getDiets();
    if (diets != null) {
      for (Diet diet : diets) {
        url += diet.getUrlPart();
      }
    }
    Long calories = getMealCalories(mealType, userParams);
    url += getCaloriesUrlPart(calories, edamamProperties.getPrecision());
    return url;
  }

  @Override
  public String getSingleUrl(RecipeQueryDto recipeQuery, UserParams userParams) {
    String url = getUrlFromProperties();
    if (recipeQuery.getMealType() != null) {
      url += recipeQuery.getMealType().getUrlPart();
      Long calories = getMealCalories(recipeQuery.getMealType(), userParams);
      url += getCaloriesUrlPart(calories, edamamProperties.getPrecision());
    }
    if (recipeQuery.getCuisineType() != null) {
      url += recipeQuery.getCuisineType().getUrlPart();
    }
    if (recipeQuery.getDishType() != null) {
      url += recipeQuery.getDishType().getUrlPart();
    }
    if (recipeQuery.getQuery() != null) {
      url += getQueryUrlPart(recipeQuery);
    }
    List<Health> healths = userParams.getHealths();
    if (healths != null) {
      for (Health health : healths) {
        url += health.getUrlPart();
      }
    }
    List<Diet> diets = userParams.getDiets();
    if (diets != null) {
      for (Diet diet : diets) {
        url += diet.getUrlPart();
      }
    }
    return url;
  }

  private Long getMealCalories(MealType mealType, UserParams userParams) {
    return Math.round(userParams.getDailyCalories() * mealType.getFraction());
  }

  private String getCaloriesUrlPart(long calories, double precision){
    return "&calories=" + (calories * (1 - precision)) + "-" + (calories * (1 + precision));
  }

  private String getQueryUrlPart(RecipeQueryDto recipeQuery){
    return "&q=" + recipeQuery.getQuery();
  }

  public String getUrlFromProperties(){
    String url = edamamProperties.getUrl();
    List<EdamamProperties.Param> params = edamamProperties.getParams();
    for(EdamamProperties.Param param : params){
      url += param.getUrlPart();
    }
    List<EdamamProperties.Field> fields = edamamProperties.getFields();
    for(EdamamProperties.Field field : fields){
      url += field.getUrlPart();
    }
    return url;
  }
}
