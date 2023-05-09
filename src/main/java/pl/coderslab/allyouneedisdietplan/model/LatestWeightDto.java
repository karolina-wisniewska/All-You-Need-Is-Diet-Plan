package pl.coderslab.allyouneedisdietplan.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

import java.time.LocalDateTime;

@Data
public class LatestWeightDto {

  private Long id;

  @Column(columnDefinition = "double(5,2)")
  @DecimalMax(value = "200.0", message = "{weight.too.high.error}")
  @DecimalMin(value = "0.0", message = "{weight.too.low.error}")
  @NotNull
  private Double weight;

  @PastOrPresent(message = "{date.from.future.error}")
  @NotNull
  @DateTimeFormat(pattern = "MM.dd.yyyy HH:mm a")
  private LocalDateTime weightingDate;

  private User user;
}
