package pl.coderslab.allyouneedisdietplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.allyouneedisdietplan.entity.DayName;
import pl.coderslab.allyouneedisdietplan.entity.Diet;

public interface DayNameRepository extends JpaRepository<DayName, Integer> {

}
