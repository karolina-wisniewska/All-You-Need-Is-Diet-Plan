package pl.coderslab.allyouneedisdietplan.service.dictionary;

import pl.coderslab.allyouneedisdietplan.entity.dictionary.MealType;

import java.util.List;

public interface MealTypeService {
  List<MealType> findAll();
  MealType findById(Integer id);
  long count();
}
