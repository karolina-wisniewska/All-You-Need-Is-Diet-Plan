package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.Health;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
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
  };

}
