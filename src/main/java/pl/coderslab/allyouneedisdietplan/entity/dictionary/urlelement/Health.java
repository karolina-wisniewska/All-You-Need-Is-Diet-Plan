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
@Table(name = "healths")
public class Health implements UrlElement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "tinyint")
  private Integer id;

  @Column(columnDefinition="varchar(30)")
  @NotNull
  private String name;

  public String getUrlPart(){
    return "&health=" + name;
  }
}
