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
    String url = getUrlFromProperties();
    List<UrlElement> userUrlElements = userParams.getAllUserUrlElements();
    if(userUrlElements != null){
      for(UrlElement urlElement : userUrlElements) {
        url += urlElement.getUrlPart();
      }
    }
    url += mealType.getUrlPart();
    Long calories = getMealCalories(mealType.getFraction(), userParams.getDailyCalories());
    url += getCaloriesUrlPart(calories, edamamProperties.getPrecision());
    return url;
  }

  @Override
  public String getSingleUrl(RecipeQueryDto recipeQuery, UserParams userParams) {
    String url = getUrlFromProperties();
    List<UrlElement> userUrlElements = userParams.getHealthsAndDietsUrlElements();
    if(userUrlElements != null){
      for(UrlElement urlElement : userUrlElements) {
        url += urlElement.getUrlPart();
      }
    }
    List<UrlElement> queryUrlElements = recipeQuery.getQueryUrlElements();
    if(queryUrlElements != null){
      for(UrlElement urlElement : queryUrlElements) {
        url += urlElement.getUrlPart();
      }
    }
    MealType queryMealType = recipeQuery.getMealType();
    if (queryMealType != null) {
      double mealTypeFraction = queryMealType.getFraction();
      Long calories = getMealCalories(mealTypeFraction, userParams.getDailyCalories());
      url += getCaloriesUrlPart(calories, edamamProperties.getPrecision());
    }
    return url;
  }

  private Long getMealCalories(double mealTypeFraction, Long dailyCalories) {
    return Math.round(dailyCalories * mealTypeFraction);
  }

  private String getCaloriesUrlPart(long calories, double precision){
    return "&calories=" + (calories * (1 - precision)) + "-" + (calories * (1 + precision));
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
