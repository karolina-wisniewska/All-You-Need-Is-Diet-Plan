package pl.coderslab.allyouneedisdietplan.model.dictionary;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ActivityLevelDto {

  private Integer id;

  @NotNull
  private String name;

  @NotNull
  private Double value;

  private String description;
}
