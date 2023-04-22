package pl.coderslab.allyouneedisdietplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.allyouneedisdietplan.entity.DishType;

public interface DishTypeRepository extends JpaRepository<DishType, Integer> {

}
