package pl.coderslab.allyouneedisdietplan.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DayNameDto {


  private Integer id;

  @NotNull
  private String name;
}
