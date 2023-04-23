package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.Diet;
import pl.coderslab.allyouneedisdietplan.entity.Health;
import pl.coderslab.allyouneedisdietplan.entity.MealType;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.repository.PlanRepository;
import pl.coderslab.allyouneedisdietplan.service.PlanService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

  private final PlanRepository planRepository;
  private final String API_URL_PART = "https://api.edamam.com/api/recipes/v2?type=public";
  private final String APP_KEY_PART = "&app_id=40fa347c&app_key=0eb977d62e50265cf4df0451172393a6";
  private final String RANDOM_PART = "&random=true";
  private final String RESULT_FIELDS_PART = "&field=label&field=shareAs&field=externalId";

  @Override
  public void save(Plan plan) {
    planRepository.save(plan);
  }

  @Override
  public Plan findFirstByUserOrderByIdDesc(User user) {
    return planRepository.findByUser(user);
  }

  @Override
  public Long getMealCalories(MealType mealType, UserDetails userDetails) {
    return Math.round(userDetails.getDailyCalories() * mealType.getFraction());
  }

  @Override
  public String getRequestUrl(MealType mealType, UserDetails userDetails) {
    String url = API_URL_PART + APP_KEY_PART + RANDOM_PART + RESULT_FIELDS_PART;
    url += "&mealType=" + mealType.getName();
    if (userDetails.getCuisineType() != null) {
      url += "&cuisineType=" + userDetails.getCuisineType().getName();
    }
    List<Health> healths = userDetails.getHealths();
    if (healths != null) {
      for (Health health : healths) {
        url += "&health=" + health.getName();
      }
    }
    List<Diet> diets = userDetails.getDiets();
    if (diets != null) {
      for (Diet diet : diets) {
        url += "&diet=" + diet.getName();
      }
    }
    Long calories = getMealCalories(mealType, userDetails);
    double precision = 0.05;
    url += "&calories=" + (calories * (1 - precision)) + "-" + (calories * (1 + precision));
    return url;
  }

}
