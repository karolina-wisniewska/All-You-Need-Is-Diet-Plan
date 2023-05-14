package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.MealType;
import pl.coderslab.allyouneedisdietplan.entity.Plan;

import java.util.List;

public interface DietPlanItemService {
  void save(DietPlanItem dietPlanItem);
  List<DietPlanItem> findByPlanAndMealTypeOrderByIdAsc(Plan plan, MealType mealType);
  DietPlanItem findById(Long id);
  int countByPlan(Plan plan);
}
