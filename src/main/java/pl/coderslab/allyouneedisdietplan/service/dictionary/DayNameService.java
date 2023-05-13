package pl.coderslab.allyouneedisdietplan.service.dictionary;

import pl.coderslab.allyouneedisdietplan.entity.dictionary.DayName;

import java.util.List;

public interface DayNameService {
  DayName findById(Integer id);
  List<DayName> findAll();

  long count();
}
