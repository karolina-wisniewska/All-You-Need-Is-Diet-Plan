package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.Recipe;
import pl.coderslab.allyouneedisdietplan.repository.RecipeRepository;
import pl.coderslab.allyouneedisdietplan.service.RecipeService;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
  private final RecipeRepository recipeRepository;

  @Override
  public Recipe save(Recipe recipe) {
    Recipe foundRecipe = findByExternalLink(recipe.getExternalLink());
    if(foundRecipe == null){
      recipeRepository.save(recipe);
      return findByExternalLink(recipe.getExternalLink());
    }
    return foundRecipe;
  }

  @Override
  public Recipe findByExternalLink(String externalLink) {
    return recipeRepository.findByExternalLink(externalLink);
  }
}


