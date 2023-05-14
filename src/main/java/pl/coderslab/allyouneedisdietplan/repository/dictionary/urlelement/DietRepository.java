package pl.coderslab.allyouneedisdietplan.repository.dictionary.urlelement;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.Diet;

public interface DietRepository extends JpaRepository<Diet, Integer> {


}
