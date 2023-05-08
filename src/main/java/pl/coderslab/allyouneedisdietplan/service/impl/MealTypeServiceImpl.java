package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.MealType;
import pl.coderslab.allyouneedisdietplan.repository.MealTypeRepository;
import pl.coderslab.allyouneedisdietplan.service.MealTypeService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MealTypeServiceImpl implements MealTypeService {
  private final MealTypeRepository mealTypeRepository;
  @Override
  public List<MealType> findAll(){
    return mealTypeRepository.findAll();
  }
  @Override
  public MealType findById(Integer id) {
    return mealTypeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }
}
