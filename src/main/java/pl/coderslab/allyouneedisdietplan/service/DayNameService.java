package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.DayName;
import pl.coderslab.allyouneedisdietplan.entity.Diet;

import java.util.List;

public interface DayNameService {
  DayName findById(Integer id);
}
