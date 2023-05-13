package pl.coderslab.allyouneedisdietplan.external.harrisbenedictequation;

import lombok.Getter;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Gender;

@Getter
public class HarrisBenedictEquation {
  private double freeCoeff;
  private double weightCoeff;
  private double heightCoeff;
  private double ageCoeff;
  private final Gender gender;

  public HarrisBenedictEquation(Gender gender) {
    this.gender = gender;
    if("male".equals(gender.getName())){
      freeCoeff = 66.5;
      weightCoeff = 13.75;
      heightCoeff = 5.0;
      ageCoeff = 6.78;
    }
    freeCoeff = 655.1;
    weightCoeff = 9.57;
    heightCoeff = 1.85;
    ageCoeff = 6.68;
  }
}
