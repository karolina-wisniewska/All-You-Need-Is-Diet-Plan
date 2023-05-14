package pl.coderslab.allyouneedisdietplan.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.DayName;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.MealType;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.entity.Recipe;

@Data
public class DietPlanItemDto {

  private Long id;

  @NotNull
  private Plan plan;

  @NotNull
  private Recipe recipe;

  @NotNull
  private DayName dayName;

  @NotNull
  private MealType mealType;
}
