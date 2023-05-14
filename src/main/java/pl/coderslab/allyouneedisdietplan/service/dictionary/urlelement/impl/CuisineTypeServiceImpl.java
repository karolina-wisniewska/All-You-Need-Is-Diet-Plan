package pl.coderslab.allyouneedisdietplan.service.dictionary.urlelement.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.CuisineType;
import pl.coderslab.allyouneedisdietplan.repository.dictionary.urlelement.CuisineTypeRepository;
import pl.coderslab.allyouneedisdietplan.service.dictionary.urlelement.CuisineTypeService;

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
