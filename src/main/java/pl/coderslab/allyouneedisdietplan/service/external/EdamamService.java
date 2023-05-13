package pl.coderslab.allyouneedisdietplan.service.external;

import pl.coderslab.allyouneedisdietplan.entity.dictionary.MealType;
import pl.coderslab.allyouneedisdietplan.entity.UserParams;
import pl.coderslab.allyouneedisdietplan.model.RecipeQueryDto;
import pl.coderslab.allyouneedisdietplan.external.edamam.RecipeResourceDto;

import java.util.List;

public interface EdamamService {

  String getUserUrl(MealType mealType, UserParams userParams);
  String getSingleUrl(RecipeQueryDto recipeQuery, UserParams userParams);
  String getUrlToShowRecipeDetails(String url);
  List<RecipeResourceDto> getRecipeResourcesFromApi(String url);
}
