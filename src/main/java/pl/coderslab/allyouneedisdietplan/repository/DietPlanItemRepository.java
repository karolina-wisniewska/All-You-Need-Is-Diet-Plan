package pl.coderslab.allyouneedisdietplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.allyouneedisdietplan.entity.Diet;
import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;

public interface DietPlanItemRepository extends JpaRepository<DietPlanItem, Integer> {


}
