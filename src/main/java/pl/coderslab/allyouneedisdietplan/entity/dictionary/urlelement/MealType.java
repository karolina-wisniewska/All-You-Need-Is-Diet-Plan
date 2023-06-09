package pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.UrlElement;

@Data
@Entity
@Table(name = "meal_types")
public class MealType implements UrlElement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "tinyint")
  private Integer id;

  @Column(columnDefinition="varchar(30)")
  @NotNull
  private String name;

  @Column(columnDefinition="double(3,2)")
  @NotNull
  private Double fraction;

  public String getUrlPart(){
    return "&mealType=" + name;
  }
}
