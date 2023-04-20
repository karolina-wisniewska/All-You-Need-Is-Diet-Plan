package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.MealType;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

import java.util.List;

public interface PlanService {

  void save(Plan plan);
  String getRequestUrl(MealType mealType, UserDetails userDetails);

  Long getMealCalories(MealType mealType, UserDetails userDetails);

}
