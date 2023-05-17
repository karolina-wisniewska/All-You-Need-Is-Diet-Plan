package pl.coderslab.allyouneedisdietplan.calculator;

import lombok.Getter;
import pl.coderslab.allyouneedisdietplan.enums.Gender;

import java.time.LocalDateTime;

@Getter
public class MetabolicRateCalculator {
  private final double freeCoeff;
  private final double weightCoeff;
  private final double heightCoeff;
  private final double ageCoeff;
  private final long CALORIC_DEFICIT_PER_KG = 7000L;

  public MetabolicRateCalculator(String genderName) {
    String maleName = Gender.MALE.name();
    if(maleName.equalsIgnoreCase(genderName)){
      this.freeCoeff = 66.5;
      this.weightCoeff = 13.75;
      this.heightCoeff = 5.0;
      this.ageCoeff = 6.78;
    } else {
      this.freeCoeff = 655.1;
      this.weightCoeff = 9.57;
      this.heightCoeff = 1.85;
      this.ageCoeff = 6.68;
    }
  }

  public Long calculate(double currentWeight, double dreamWeight, int height, int userAge, double activityLevel){
    double basalMetabolicRate = getFreeCoeff() + (getWeightCoeff() * currentWeight + (getHeightCoeff() * height) - (getAgeCoeff() * userAge));
    double totalMetabolicRate = basalMetabolicRate * activityLevel + getGainOrLoseCoeff(currentWeight - dreamWeight) * getCaloricDifference(currentWeight - dreamWeight);
    return Math.round(totalMetabolicRate);
  }

  private int getCaloricDifference(double weightDifference) {
    return Math.abs(weightDifference) >= 15.0 ? 1000 : 500;
  }

  private int getGainOrLoseCoeff(double weightDifference) {
    int gainLoseCoeff = -1;
    if (weightDifference < 0) {
      gainLoseCoeff = 1;
    } else if (weightDifference == 0) {
      gainLoseCoeff = 0;
    }
    return gainLoseCoeff;
  }

  public LocalDateTime calculateSuccessDate(double weightDifference) {
    long daysToSuccess = Math.abs(Math.round((weightDifference * CALORIC_DEFICIT_PER_KG) / getCaloricDifference(weightDifference)));
    return LocalDateTime.now().plusDays(daysToSuccess);
  }
}
