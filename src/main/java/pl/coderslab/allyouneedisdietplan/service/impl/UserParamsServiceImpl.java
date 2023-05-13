package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.calculator.MetabolicRateCalculator;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.UserParams;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.ActivityLevel;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Gender;
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
    Gender gender = userParams.getGender();
    LocalDate dateOfBirth = userParams.getDateOfBirth();
    int age = calculateUserAge(dateOfBirth);
    int height = userParams.getHeight();
    ActivityLevel activityLevel = userParams.getActivityLevel();
    double activityLevelValue = activityLevel.getValue();
    Double currentWeight = getUserWeight(userParams.getUser());
    Double dreamWeight = userParams.getDreamWeight();

    MetabolicRateCalculator metabolicRateCalculator = new MetabolicRateCalculator(gender.getName());
    return metabolicRateCalculator.calculate(currentWeight, dreamWeight, height, age, activityLevelValue);
  }

  @Override
  public String getSuccessDateMessage(UserParams userParams) {
    double weightDifference = getUserWeight(userParams.getUser())-userParams.getDreamWeight();
    if (weightDifference == 0) {
      return "Congrats! You've achieved your Dream Weight! Keep it up!";
    }
    Gender gender = userParams.getGender();
    MetabolicRateCalculator metabolicRateCalculator = new MetabolicRateCalculator(gender.getName());
    LocalDateTime successDate = metabolicRateCalculator.calculateSuccessDate(weightDifference);
    return "You will achieve your dream weight on " + successDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
  }
  private int calculateUserAge(LocalDate userBirthDate) {
    Period period = Period.between(userBirthDate, LocalDate.now());
    return period.getYears();
  }
  private double getUserWeight(User user){
    LatestWeight userLatestWeight = latestWeightService.findFirstByUserOrderByWeightingDateDesc(user);
      return userLatestWeight.getWeight();
  }
}
