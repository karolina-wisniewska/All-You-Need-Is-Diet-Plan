package pl.coderslab.allyouneedisdietplan.repository.dictionary;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.DishType;

public interface DishTypeRepository extends JpaRepository<DishType, Integer> {

}
