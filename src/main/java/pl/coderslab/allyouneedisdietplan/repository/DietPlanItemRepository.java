package pl.coderslab.allyouneedisdietplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.Plan;

import java.util.List;

public interface DietPlanItemRepository extends JpaRepository<DietPlanItem, Integer> {
  List<DietPlanItem> findByPlanOrderByIdAsc(Plan plan);

}
