package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.repository.PlanRepository;
import pl.coderslab.allyouneedisdietplan.service.PlanService;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

  private final PlanRepository planRepository;

  @Override
  public void save(Plan plan) {
    planRepository.save(plan);
  }

  public String getRequestUrl(String mealType, UserDetails userDetails) {
    String url = "https://api.edamam.com/api/recipes/v2?type=public&app_id=40fa347c&app_key=0eb977d62e50265cf4df0451172393a6";
    return url;
  }

}
