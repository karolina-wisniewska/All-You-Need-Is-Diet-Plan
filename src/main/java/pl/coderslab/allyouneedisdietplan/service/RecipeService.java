package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.Recipe;
import pl.coderslab.allyouneedisdietplan.external.edamam.RecipeResourceDto;

import java.util.List;

public interface RecipeService {
  Recipe save(Recipe recipe);
  Recipe findByExternalLink(String externalLink);
  Recipe createFromResourceListByPosition(int position, List<RecipeResourceDto>recipeResources);

}
