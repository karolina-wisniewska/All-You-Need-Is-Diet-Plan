package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.MealType;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.model.RecipeQueryDto;
import pl.coderslab.allyouneedisdietplan.model.json.RecipeResourceDto;

import java.util.List;

public interface ProviderService {

  String getUserUrl(MealType mealType, UserDetails userDetails);
  String getSingleUrl(RecipeQueryDto recipeQuery, UserDetails userDetails);
  String getUrlToShowRecipeDetails(String url);
  List<RecipeResourceDto> getRecipeResourcesFromApi(String url);
}
