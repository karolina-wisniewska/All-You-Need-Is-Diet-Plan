package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.ActivityLevel;
import pl.coderslab.allyouneedisdietplan.entity.CuisineType;

import java.util.List;

public interface CuisineTypeService {
  List<CuisineType> findAll();

}
