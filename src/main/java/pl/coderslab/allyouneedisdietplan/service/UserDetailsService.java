package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

public interface UserDetailsService {

  void save(UserDetails userDetails);
  UserDetails findByUser(User user);

  Long calculateDailyCalories(UserDetails userDetails);
}
