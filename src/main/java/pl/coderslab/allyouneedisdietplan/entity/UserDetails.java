package pl.coderslab.allyouneedisdietplan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user_details")
public class UserDetails {

  @Id
  @Column(name = "user_id")
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private Gender gender;

  @Column(columnDefinition="tinyint unsigned")
  @Range(min = 18, max = 100, message = "{age.out.of.range.error}")
  @NotNull
  private Integer age;

  @Column(columnDefinition="tinyint unsigned")
  @Range(min = 100, max = 200, message = "{height.out.of.range.error}")
  @NotNull
  private Integer height;

  @Column(columnDefinition="double(5,2)")
  @DecimalMax(value = "200.0", message = "{weight.too.high.error}")
  @DecimalMin(value = "0.0", message = "{weight.too.low.error}")
  @NotNull
  private Double dreamWeight;

  @Column(name = "daily_calories", columnDefinition="int unsigned")
  private Long dailyCalories;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "activity_level_id")
  @NotNull
  private ActivityLevel activityLevel;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cuisine_type_id")
  private CuisineType cuisineType;

  @ManyToMany
  @JoinTable(name = "users_healths",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "health_id"))
  private List<Health> healths = new ArrayList<>();

  @ManyToMany
  @JoinTable(name = "users_diets",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "diet_id"))
  private List<Diet> diets = new ArrayList<>();
}
