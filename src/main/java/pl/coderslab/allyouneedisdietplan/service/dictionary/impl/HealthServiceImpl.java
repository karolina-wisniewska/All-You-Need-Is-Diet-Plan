package pl.coderslab.allyouneedisdietplan.service.dictionary.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Health;
import pl.coderslab.allyouneedisdietplan.repository.dictionary.HealthRepository;
import pl.coderslab.allyouneedisdietplan.service.dictionary.HealthService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HealthServiceImpl implements HealthService {

  private final HealthRepository healthRepository;
  @Override
  public List<Health> findAll(){
    return healthRepository.findAll();
  }

}
