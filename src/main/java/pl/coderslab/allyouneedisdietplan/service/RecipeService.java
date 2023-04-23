package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.Recipe;

public interface RecipeService {
  Recipe save(Recipe recipe);
  Recipe findByExternalLink(String externalLink);

}
