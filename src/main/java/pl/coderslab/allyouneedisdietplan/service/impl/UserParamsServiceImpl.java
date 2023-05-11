package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.UserParams;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.repository.UserParamsRepository;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;
import pl.coderslab.allyouneedisdietplan.service.UserParamsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class UserParamsServiceImpl implements UserParamsService {

  private final UserParamsRepository userParamsRepository;
  private final LatestWeightService latestWeightService;
  private final long CALORIC_DEFICIT_PER_KG = 7000L;

  @Override
  public void save(UserParams userParams) {
    userParamsRepository.save(userParams);
  }

  @Override
  public UserParams findByUser(User user) {
    return userParamsRepository.findByUser(user);
  }

  @Override
  public Long calculateDailyCalories(UserParams userParams) {
    double a = 655.1;
    double b = 9.563;
    double c = 1.85;
    double d = 4.676;
    int e = -1;
    Double currentWeight = latestWeightService.findFirstByUserOrderByWeightingDateDesc(userParams.getUser()).getWeight();
    Double dreamWeight = userParams.getDreamWeight();
    double weightDifference = currentWeight - dreamWeight;
    String userGender = userParams.getGender().getName();
    if ("male".equals(userGender)) {
      a = 66.5;
      b = 13.75;
      c = 5.0;
      d = 6.775;
    }
    if (weightDifference < 0) {
      e = 1;
    } else if (weightDifference == 0) {
      e = 0;
    }
    int userAge = Period.between(userParams.getDateOfBirth(), LocalDate.now()).getYears();
    double basalMetabolicRate = a + (b * currentWeight) + (c * userParams.getHeight()) - (d * userAge);
    return Math.round(basalMetabolicRate * userParams.getActivityLevel().getValue() + e * calculateCaloricDifference(weightDifference));
  }

  @Override
  public String calculateSuccessDate(UserParams userParams) {
    Double currentWeight = latestWeightService.findFirstByUserOrderByWeightingDateDesc(userParams.getUser()).getWeight();
    Double dreamWeight = userParams.getDreamWeight();
    double weightDifference = currentWeight - dreamWeight;
    if(weightDifference == 0){
      return "Congrats! You've achieved your Dream Weight! Keep it up!";
    }
    long daysToSuccess = Math.abs(Math.round((weightDifference * CALORIC_DEFICIT_PER_KG) / calculateCaloricDifference(weightDifference)));
    LocalDateTime successDate = LocalDateTime.now().plusDays(daysToSuccess);
    return "You will achieve your dream weight on " + successDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
  }

  private int calculateCaloricDifference(double weightDifference){
    int caloricDifference = 500;
    if(Math.abs(weightDifference) >= 15.0){
      caloricDifference = 1000;
    }
    return caloricDifference;
  }

}
