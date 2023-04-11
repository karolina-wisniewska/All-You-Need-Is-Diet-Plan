package pl.coderslab.allyouneedisdietplan.entity.security;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition="varchar(30)", name = "user_name")
  @Length(min = 5, message = "*Your user name must have at least 5 characters")
  @NotEmpty(message = "*Please provide a user name")
  @NotNull
  private String userName;

  @Column(columnDefinition="text")
  @Length(min = 5, message = "*Your password must have at least 5 characters")
  @NotEmpty(message = "*Please provide your password")
  @NotNull
  private String password;

  private Boolean active;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
  @PrimaryKeyJoinColumn
  private UserDetails userDetails;
}
