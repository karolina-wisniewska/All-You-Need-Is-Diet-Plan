package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.Diet;
import pl.coderslab.allyouneedisdietplan.entity.Health;
import pl.coderslab.allyouneedisdietplan.repository.DietRepository;
import pl.coderslab.allyouneedisdietplan.repository.HealthRepository;
import pl.coderslab.allyouneedisdietplan.service.DietService;
import pl.coderslab.allyouneedisdietplan.service.HealthService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DietServiceImpl implements DietService {
  private final DietRepository dietRepository;
  @Override
  public List<Diet> findAll(){
    return dietRepository.findAll();
  }

}
