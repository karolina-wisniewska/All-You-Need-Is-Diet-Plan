package pl.coderslab.allyouneedisdietplan.repository.dictionary;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Diet;

public interface DietRepository extends JpaRepository<Diet, Integer> {


}
