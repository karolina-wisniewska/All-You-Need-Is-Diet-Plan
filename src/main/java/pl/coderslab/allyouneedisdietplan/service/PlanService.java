package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.MealType;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

public interface PlanService {

  void save(Plan plan);
  Plan findFirstByUserOrderByIdDesc(User user);
  String getRequestUrl(MealType mealType, UserDetails userDetails);
  Long getMealCalories(MealType mealType, UserDetails userDetails);

}
