package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.Health;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.repository.HealthRepository;
import pl.coderslab.allyouneedisdietplan.repository.UserDetailsRepository;
import pl.coderslab.allyouneedisdietplan.service.HealthService;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;
import pl.coderslab.allyouneedisdietplan.service.UserDetailsService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserDetailsRepository userDetailsRepository;
  private final LatestWeightService latestWeightService;

  @Override
  public void save(UserDetails userDetails) {
    userDetailsRepository.save(userDetails);
  }

  @Override
  public UserDetails findByUser(User user) {
    return userDetailsRepository.findByUser(user);
  }

  @Override
  public Long calculateDailyCalories(UserDetails userDetails) {
    double a = 655.1;
    double b = 9.563;
    double c = 1.85;
    double d = 4.676;
    int e = -350;
    Double currentWeight = latestWeightService.findFirstByUserOrderByIdDesc(userDetails.getUser()).getWeight();
    String userGender = userDetails.getGender().getName();
    if ("male".equals(userGender)) {
      a = 66.5;
      b = 13.75;
      c = 5.0;
      d = 6.775;
    }
    if(currentWeight < userDetails.getDreamWeight()){
      e = 350;
    }
    double basalMetabolicRate = a + (b * currentWeight) + (c * userDetails.getHeight()) - (d * userDetails.getAge());
    return Math.round(basalMetabolicRate * userDetails.getActivityLevel().getValue() + e);
  }

  @Override
  public LocalDateTime calculateSuccessDate(UserDetails userDetails) {
    return null;
  }


}
