package pl.coderslab.allyouneedisdietplan.service.external.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.allyouneedisdietplan.entity.UserParams;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.UrlElement;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.MealType;
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
  public String getUserUrlPerMeal(MealType mealType, UserParams userParams) {
    StringBuilder urlBuilder = new StringBuilder(getUrlFromProperties());

    List<UrlElement> urlElements = userParams.getUserUrlElementsForUserQuery();
    urlElements.forEach(element -> urlBuilder.append(element.getUrlPart()));

    double mealTypeFraction = mealType.getFraction();
    Long dailyCalories = userParams.getDailyCalories();
    double precision = edamamProperties.getPrecision();
    urlBuilder.append(getCaloriesUrlPart(mealTypeFraction, dailyCalories, precision));

    return urlBuilder.toString();
  }

  @Override
  public String getSingleUrl(RecipeQueryDto recipeQuery, UserParams userParams) {
    StringBuilder urlBuilder = new StringBuilder(getUrlFromProperties());

    List<UrlElement> userUrlElements = userParams.getUserUrlElementsForSingleQuery();
    userUrlElements.forEach(element -> urlBuilder.append(element.getUrlPart()));

    List<UrlElement> queryUrlElements = recipeQuery.getQueryUrlElements();
    queryUrlElements.forEach(element -> urlBuilder.append(element.getUrlPart()));

    MealType queryMealType = recipeQuery.getMealType();
    double mealTYpeFraction = queryMealType.getFraction();
    Long dailyCalories = userParams.getDailyCalories();
    double precision = edamamProperties.getPrecision();
    urlBuilder.append(getCaloriesUrlPart(mealTYpeFraction, dailyCalories, precision));

    return urlBuilder.toString();
  }

  private String getCaloriesUrlPart(double mealTypeFraction, Long dailyCalories, double precision) {
    long mealCalories = Math.round(dailyCalories * mealTypeFraction);
    return "&calories=" + (mealCalories * (1 - precision)) + "-" + (mealCalories * (1 + precision));
  }

  public String getUrlFromProperties() {
    StringBuilder urlBuilder = new StringBuilder(edamamProperties.getUrl());

    List<UrlElement> propertiesUrlElements = edamamProperties.getPropertiesUrlElements();
    propertiesUrlElements.forEach(element -> urlBuilder.append(element.getUrlPart()));

    return urlBuilder.toString();
  }
}
