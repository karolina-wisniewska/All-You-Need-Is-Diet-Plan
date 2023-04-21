package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.Recipe;

public interface RecipeService {
  void save(Recipe recipe);
  boolean existsByExternalLink(String externalLink);

}
