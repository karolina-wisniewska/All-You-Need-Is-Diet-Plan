package pl.coderslab.allyouneedisdietplan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "diet_plan_items")
public class DietPlanItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private Plan plan;

  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private Recipe recipe;

  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private DayName dayName;

  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private MealType mealType;
}
