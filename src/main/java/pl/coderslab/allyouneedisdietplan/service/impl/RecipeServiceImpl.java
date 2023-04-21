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
  public void save(Recipe recipe) {
    if(!existsByExternalLink(recipe.getExternalLink())){
      recipeRepository.save(recipe);
    }
  }

  @Override
  public boolean existsByExternalLink(String externalLink) {
    return recipeRepository.existsByExternalLink(externalLink);
  }
}


