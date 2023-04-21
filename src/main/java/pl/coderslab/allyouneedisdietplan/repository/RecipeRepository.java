package pl.coderslab.allyouneedisdietplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.allyouneedisdietplan.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
  boolean existsByExternalLink(String externalLink);
}
