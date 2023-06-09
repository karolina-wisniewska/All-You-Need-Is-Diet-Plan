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
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.ActivityLevel;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.UrlElement;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.CuisineType;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.Diet;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.Health;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.enums.Gender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@Table(name = "user_params")
public class UserParams {

  @Id
  @Column(name = "user_id")
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "user_id")
  private User user;

  private Gender gender;

  @NotNull
  private LocalDate dateOfBirth;

  @Column(columnDefinition="tinyint unsigned")
  @NotNull
  private Integer height;

  @Column(columnDefinition="double(5,2)")
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

  public List<UrlElement> getUserUrlElementsForUserQuery(){
    List<UrlElement> urlElements = new ArrayList<>();
    urlElements = addCuisineTypeUrlElementToList(urlElements);
    urlElements = addDietUrlElementsToList(urlElements);
    urlElements = addHealthUrlElementsToList(urlElements);
    return urlElements;
  }

  public List<UrlElement> getUserUrlElementsForSingleQuery(){
    List<UrlElement> urlElements = new ArrayList<>();
    urlElements = addDietUrlElementsToList(urlElements);
    urlElements = addHealthUrlElementsToList(urlElements);
    return urlElements;
  }
  public List<UrlElement> addHealthUrlElementsToList(List<UrlElement> list){
    list.addAll(healths);
    return list;
  }
  public List<UrlElement> addDietUrlElementsToList(List<UrlElement> list){
    list.addAll(diets);
    return list;
  }
  public List<UrlElement> addCuisineTypeUrlElementToList(List<UrlElement> list){
    Optional.ofNullable(cuisineType).map(list::add);
    return list;
  }


}
