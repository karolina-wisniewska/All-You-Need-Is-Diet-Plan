package pl.coderslab.allyouneedisdietplan.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class IsAdultValidator implements ConstraintValidator<IsAdult, LocalDate> {
  @Override
  public void initialize(IsAdult constraintAnnotation) {
  }
  @Override
  public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
    if ( value == null ) {
      return true;
    }
    return value.isBefore(LocalDate.now().minusYears(18L)) && value.isAfter(LocalDate.now().minusYears(100L));
  }
}
