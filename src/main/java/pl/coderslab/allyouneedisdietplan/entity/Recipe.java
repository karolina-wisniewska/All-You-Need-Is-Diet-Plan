package pl.coderslab.allyouneedisdietplan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(name = "recipes")
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition="varchar(255)")
  @NotNull
  private String label;

  @Column(columnDefinition="varchar(255)")
  @NotNull
  private String externalLink;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Recipe recipe)) return false;
    return label.equals(recipe.label) && externalLink.equals(recipe.externalLink);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, externalLink);
  }
}
