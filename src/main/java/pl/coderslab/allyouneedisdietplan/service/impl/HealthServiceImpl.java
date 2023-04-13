package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.CuisineType;
import pl.coderslab.allyouneedisdietplan.entity.Health;
import pl.coderslab.allyouneedisdietplan.repository.CuisineTypeRepository;
import pl.coderslab.allyouneedisdietplan.repository.HealthRepository;
import pl.coderslab.allyouneedisdietplan.service.CuisineTypeService;
import pl.coderslab.allyouneedisdietplan.service.HealthService;

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
