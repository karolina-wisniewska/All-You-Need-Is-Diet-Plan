package pl.coderslab.allyouneedisdietplan.service.security;

import pl.coderslab.allyouneedisdietplan.entity.security.Role;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

public interface UserService {

  User findUserByUserName(String login);
  User saveUser(User user);
  User findByRole(Role role);


}
