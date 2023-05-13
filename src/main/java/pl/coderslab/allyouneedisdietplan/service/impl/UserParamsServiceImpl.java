package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.UserParams;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.ActivityLevel;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Gender;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.external.harrisbenedictequation.HarrisBenedictEquation;
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
    Gender userGender = userParams.getGender();
    LocalDate userBirthDate = userParams.getDateOfBirth();
    int userAge = getUserAge(userBirthDate);
    ActivityLevel userActivityLevel = userParams.getActivityLevel();

    HarrisBenedictEquation hBEquation = new HarrisBenedictEquation(userGender);

    double weightDifference = getWeightDifference(userParams);
    int gainOrLoseCoeff = getGainOrLoseCoeff(weightDifference);

    double basalMetabolicRate = hBEquation.getFreeCoeff() + (hBEquation.getWeightCoeff() * getUserWeight(userParams.getUser()) + (hBEquation.getHeightCoeff() * userParams.getHeight()) - (hBEquation.getAgeCoeff() * userAge));
    double totalMetabolicRate = basalMetabolicRate * userActivityLevel.getValue() + gainOrLoseCoeff * getCaloricDifference(weightDifference);
    return Math.round(totalMetabolicRate);
  }

  @Override
  public String calculateSuccessDate(UserParams userParams) {
    double weightDifference = getWeightDifference(userParams);
    if (weightDifference == 0) {
      return "Congrats! You've achieved your Dream Weight! Keep it up!";
    }
    long daysToSuccess = Math.abs(Math.round((weightDifference * CALORIC_DEFICIT_PER_KG) / getCaloricDifference(weightDifference)));
    LocalDateTime successDate = LocalDateTime.now().plusDays(daysToSuccess);
    return "You will achieve your dream weight on " + successDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
  }

  public double getWeightDifference(UserParams userParams){
    Double currentWeight = getUserWeight(userParams.getUser());
    Double dreamWeight = userParams.getDreamWeight();
    return currentWeight - dreamWeight;
  }

  private int getCaloricDifference(double weightDifference) {
    int caloricDifference = 500;
    if (Math.abs(weightDifference) >= 15.0) {
      caloricDifference = 1000;
    }
    return caloricDifference;
  }

  private int getGainOrLoseCoeff(double weightDifference) {
    int gainLossCoeff = -1;
    if (weightDifference < 0) {
      gainLossCoeff = 1;
    } else if (weightDifference == 0) {
      gainLossCoeff = 0;
    }
    return gainLossCoeff;
  }

  private int getUserAge(LocalDate userBirthDate) {
    Period period = Period.between(userBirthDate, LocalDate.now());
    return period.getYears();
  }

  private double getUserWeight(User user){
    LatestWeight userLatestWeight = latestWeightService.findFirstByUserOrderByWeightingDateDesc(user);
      return userLatestWeight.getWeight();
  }
}
