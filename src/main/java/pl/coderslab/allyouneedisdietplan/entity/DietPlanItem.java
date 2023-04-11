package pl.coderslab.allyouneedisdietplan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "diet_plan_items")
public class DietPlanItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Plan plan;

  @ManyToOne(fetch = FetchType.LAZY)
  private Recipe recipe;

  @ManyToOne(fetch = FetchType.LAZY)
  private DayName dayName;

  @ManyToOne(fetch = FetchType.LAZY)
  private MealType mealType;
}
