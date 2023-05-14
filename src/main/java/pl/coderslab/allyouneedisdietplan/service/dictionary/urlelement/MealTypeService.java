package pl.coderslab.allyouneedisdietplan.service.dictionary.urlelement;

import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.MealType;

import java.util.List;

public interface MealTypeService {
  List<MealType> findAll();
  MealType findById(Integer id);
  long count();
}
