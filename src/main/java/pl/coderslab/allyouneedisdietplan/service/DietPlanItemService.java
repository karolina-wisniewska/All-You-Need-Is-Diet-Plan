package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.Plan;

import java.util.List;

public interface DietPlanItemService {
  void save(DietPlanItem dietPlanItem);
  List<DietPlanItem> findByPlanOrderByIdAsc(Plan plan);

  DietPlanItem findById(Long id);
}
