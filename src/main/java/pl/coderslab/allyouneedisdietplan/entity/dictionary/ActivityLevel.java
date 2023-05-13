package pl.coderslab.allyouneedisdietplan.entity.dictionary;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "activity_levels")
public class ActivityLevel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "tinyint")
  private Integer id;

  @Column(columnDefinition="varchar(30)")
  @NotNull
  private String name;

  @Column(columnDefinition="double(3,2)")
  @NotNull
  private Double value;

  private String description;
}
