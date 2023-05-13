package pl.coderslab.allyouneedisdietplan.model;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.ActivityLevel;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.CuisineType;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Diet;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Gender;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Health;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.validator.IsAdult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserParamsDto {

  private Long id;

  private User user;

  @NotNull
  private Gender gender;

  @IsAdult
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateOfBirth;

  @Range(min = 100, max = 200, message = "{height.out.of.range.error}")
  @NotNull
  private Integer height;

  @DecimalMax(value = "200.0", message = "{weight.too.high.error}")
  @DecimalMin(value = "0.0", message = "{weight.too.low.error}")
  @NotNull
  private Double dreamWeight;

  private Long dailyCalories;

  @NotNull
  private ActivityLevel activityLevel;

  @ManyToOne(fetch = FetchType.LAZY)
  private CuisineType cuisineType;

  private List<Health> healths = new ArrayList<>();

  private List<Diet> diets = new ArrayList<>();
}
