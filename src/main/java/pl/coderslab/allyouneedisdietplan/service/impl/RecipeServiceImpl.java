package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.Recipe;
import pl.coderslab.allyouneedisdietplan.model.json.RecipeResource;
import pl.coderslab.allyouneedisdietplan.repository.RecipeRepository;
import pl.coderslab.allyouneedisdietplan.service.RecipeService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
  private final RecipeRepository recipeRepository;

  @Override
  public Recipe save(Recipe recipe) {
    Recipe foundRecipe = findByExternalLink(recipe.getExternalLink());
    if(foundRecipe == null){
      return recipeRepository.save(recipe);
    }
    return foundRecipe;
  }

  @Override
  public Recipe findByExternalLink(String externalLink) {
    return recipeRepository.findByExternalLink(externalLink);
  }
  @Override
  public Recipe createFromResourceListByPosition(int position, List<RecipeResource> recipes) {
    Recipe recipe = new Recipe();
    recipe.setLabel(recipes.get(position).getRecipe().getLabel());
    recipe.setExternalLink(recipes.get(position).getLinks().getSelf().getHref());
    return recipe;
  }
}


