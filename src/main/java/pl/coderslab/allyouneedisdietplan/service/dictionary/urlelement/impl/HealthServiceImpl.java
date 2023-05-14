package pl.coderslab.allyouneedisdietplan.service.dictionary.urlelement.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.Health;
import pl.coderslab.allyouneedisdietplan.repository.dictionary.urlelement.HealthRepository;
import pl.coderslab.allyouneedisdietplan.service.dictionary.urlelement.HealthService;

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
