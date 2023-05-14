package pl.coderslab.allyouneedisdietplan.service.dictionary.urlelement.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.DishType;
import pl.coderslab.allyouneedisdietplan.repository.dictionary.urlelement.DishTypeRepository;
import pl.coderslab.allyouneedisdietplan.service.dictionary.urlelement.DishTypeService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DishTypeServiceImpl implements DishTypeService {
  private final DishTypeRepository dishTypeRepository;
  @Override
  public List<DishType> findAll(){
    return dishTypeRepository.findAll();
  }

}
