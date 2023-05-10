package pl.coderslab.allyouneedisdietplan.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DishTypeDto {

  private Integer id;

  @NotNull
  private String name;
}
