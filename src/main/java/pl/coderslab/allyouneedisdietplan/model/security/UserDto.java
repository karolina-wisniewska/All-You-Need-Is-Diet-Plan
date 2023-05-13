package pl.coderslab.allyouneedisdietplan.model.security;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDto {

  private Long id;

  @Length(min = 5, message = "{userName.too.short.error}")
  @NotEmpty(message = "{userName.not.empty.error}")
  @NotNull
  private String userName;

  @Length(min = 5, message = "{password.too.short.error}")
  @NotEmpty(message = "{password.not.empty.error}")
  @NotNull
  private String password;
}
