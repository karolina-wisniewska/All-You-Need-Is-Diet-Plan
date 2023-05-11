package pl.coderslab.allyouneedisdietplan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "latest_weights")
public class LatestWeight {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private Double weight;

  @NotNull
  private LocalDateTime weightingDate;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;
}
