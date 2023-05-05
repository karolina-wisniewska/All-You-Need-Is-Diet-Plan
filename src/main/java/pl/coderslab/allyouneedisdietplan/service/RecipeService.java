package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.Recipe;
import pl.coderslab.allyouneedisdietplan.model.json.RecipeResource;

import java.util.List;

public interface RecipeService {
  Recipe save(Recipe recipe);
  Recipe findByExternalLink(String externalLink);
  Recipe createFromResourceListByPosition(int position, List<RecipeResource>recipeResources);

}
