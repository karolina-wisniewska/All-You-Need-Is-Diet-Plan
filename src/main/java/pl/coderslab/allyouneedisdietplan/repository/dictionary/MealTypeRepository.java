package pl.coderslab.allyouneedisdietplan.repository.dictionary;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.MealType;

public interface MealTypeRepository extends JpaRepository<MealType, Integer> {


}
