package pl.coderslab.allyouneedisdietplan.service.dictionary.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.CuisineType;
import pl.coderslab.allyouneedisdietplan.repository.dictionary.CuisineTypeRepository;
import pl.coderslab.allyouneedisdietplan.service.dictionary.CuisineTypeService;

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
