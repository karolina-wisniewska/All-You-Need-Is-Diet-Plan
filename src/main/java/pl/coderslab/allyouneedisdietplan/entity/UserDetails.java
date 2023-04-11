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
import lombok.Data;

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
  private Gender gender;

  @Column(columnDefinition="tinyint unsigned")
  private Integer age;

  @Column(columnDefinition="tinyint unsigned")
  private Integer height;

  @Column(columnDefinition="double(5,2)")
  private Double dreamWeight;

  @Column(name = "success_date")
  private LocalDateTime successDate;

  @Column(name = "daily_calories", columnDefinition="tinyint unsigned")
  private Integer dailyCalories;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "activity_level_id")
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
