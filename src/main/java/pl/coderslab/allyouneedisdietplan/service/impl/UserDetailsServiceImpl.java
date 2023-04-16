package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.Health;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.repository.HealthRepository;
import pl.coderslab.allyouneedisdietplan.repository.UserDetailsRepository;
import pl.coderslab.allyouneedisdietplan.service.HealthService;
import pl.coderslab.allyouneedisdietplan.service.UserDetailsService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserDetailsRepository userDetailsRepository;
  @Override
  public void save(UserDetails userDetails){
    userDetailsRepository.save(userDetails);
  }

  @Override
  public UserDetails findById(Long id) {
    return userDetailsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public UserDetails findByUser(User user) {
    return userDetailsRepository.findByUser(user);
  }


}
