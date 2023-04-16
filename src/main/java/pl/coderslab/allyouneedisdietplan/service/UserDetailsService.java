package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

import java.awt.print.Book;

public interface UserDetailsService {

  void save(UserDetails userDetails);
  UserDetails findById(Long id);

  UserDetails findByUser(User user);
}
