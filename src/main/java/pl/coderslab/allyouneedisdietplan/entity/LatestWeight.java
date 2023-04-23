package pl.coderslab.allyouneedisdietplan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
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

  @PastOrPresent(message = "{date.from.future.error}")
  @NotNull
  @DateTimeFormat(pattern = "MM.dd.yyyy. HH:mm a")
  private LocalDateTime weightingDate;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;
}
