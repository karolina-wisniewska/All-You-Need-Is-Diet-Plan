package pl.coderslab.allyouneedisdietplan.repository.dictionary;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.DayName;

public interface DayNameRepository extends JpaRepository<DayName, Integer> {
}
