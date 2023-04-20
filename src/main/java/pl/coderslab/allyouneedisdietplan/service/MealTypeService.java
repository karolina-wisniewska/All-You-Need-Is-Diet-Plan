package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.Diet;
import pl.coderslab.allyouneedisdietplan.entity.MealType;

import java.util.List;

public interface MealTypeService {
  List<MealType> findAll();
}
