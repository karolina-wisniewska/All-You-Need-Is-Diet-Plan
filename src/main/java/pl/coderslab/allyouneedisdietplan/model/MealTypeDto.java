package pl.coderslab.allyouneedisdietplan.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MealTypeDto {

  private Integer id;

  @NotNull
  private String name;

  @NotNull
  private Double fraction;
}
