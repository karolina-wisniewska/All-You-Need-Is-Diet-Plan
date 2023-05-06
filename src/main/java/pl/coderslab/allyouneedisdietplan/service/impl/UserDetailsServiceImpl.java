package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.repository.UserDetailsRepository;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;
import pl.coderslab.allyouneedisdietplan.service.UserDetailsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserDetailsRepository userDetailsRepository;
  private final LatestWeightService latestWeightService;
  private final int caloricDeficit = 500;
  private final long caloricDeficitPerKg = 7000L;

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
    int e = -1;
    Double currentWeight = latestWeightService.findFirstByUserOrderByIdDesc(userDetails.getUser()).getWeight();
    String userGender = userDetails.getGender().getName();
    if ("male".equals(userGender)) {
      a = 66.5;
      b = 13.75;
      c = 5.0;
      d = 6.775;
    }
    if (currentWeight < userDetails.getDreamWeight()) {
      e = 1;
    } else if (currentWeight == userDetails.getDreamWeight()) {
      e = 0;
    }
    int userAge = Period.between(userDetails.getDateOfBirth(), LocalDate.now()).getYears();
    double basalMetabolicRate = a + (b * currentWeight) + (c * userDetails.getHeight()) - (d * userAge);
    return Math.round(basalMetabolicRate * userDetails.getActivityLevel().getValue() + e * caloricDeficit);
  }

  @Override
  public String calculateSuccessDate(UserDetails userDetails) {
    Double currentWeight = latestWeightService.findFirstByUserOrderByIdDesc(userDetails.getUser()).getWeight();
    Double weightDifference = currentWeight - userDetails.getDreamWeight();
    Long daysToSuccess = Math.abs(Math.round((weightDifference * caloricDeficitPerKg) / caloricDeficit));
    LocalDateTime successDate = LocalDateTime.now().plusDays(daysToSuccess);
    return successDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
  }


}
