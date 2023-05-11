package pl.coderslab.allyouneedisdietplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.allyouneedisdietplan.entity.CuisineType;

public interface CuisineTypeRepository extends JpaRepository<CuisineType, Integer> {


}
