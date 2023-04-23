package pl.coderslab.allyouneedisdietplan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

@Data
@Entity
@Table(name = "plans")
public class Plan {

  @Id
  @Column(name = "user_id")
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "user_id")
  private User user;
}
