package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.DayName;

import java.util.List;

public interface DayNameService {
  DayName findById(Integer id);
  List<DayName> findAll();

  long count();
}
