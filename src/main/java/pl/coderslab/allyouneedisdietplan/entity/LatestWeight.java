package pl.coderslab.allyouneedisdietplan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "latest_weights")
public class LatestWeight {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "double(5,2)")
  @DecimalMax(value = "200.0", message = "{weight.too.high.error}")
  @DecimalMin(value = "0.0", message = "{weight.too.low.error}")
  @NotNull
  private Double weight;

  private LocalDateTime weightingDate;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;
}
