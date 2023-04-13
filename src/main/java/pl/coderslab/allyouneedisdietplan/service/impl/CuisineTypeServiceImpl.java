package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.ActivityLevel;
import pl.coderslab.allyouneedisdietplan.entity.CuisineType;
import pl.coderslab.allyouneedisdietplan.repository.ActivityLevelRepository;
import pl.coderslab.allyouneedisdietplan.repository.CuisineTypeRepository;
import pl.coderslab.allyouneedisdietplan.service.ActivityLevelService;
import pl.coderslab.allyouneedisdietplan.service.CuisineTypeService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CuisineTypeServiceImpl implements CuisineTypeService {

  private final CuisineTypeRepository cuisineTypeRepository;
  @Override
  public List<CuisineType> findAll(){
    return cuisineTypeRepository.findAll();
  }

}
