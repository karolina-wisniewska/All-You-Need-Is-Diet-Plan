package pl.coderslab.allyouneedisdietplan.model.dictionary;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Gender;

@Data
public class GenderDto {

  private Integer id;

  @NotNull
  private Gender name;
}
