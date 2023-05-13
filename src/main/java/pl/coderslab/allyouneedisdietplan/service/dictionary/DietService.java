package pl.coderslab.allyouneedisdietplan.service.dictionary;

import pl.coderslab.allyouneedisdietplan.entity.dictionary.Diet;

import java.util.List;

public interface DietService {
  List<Diet> findAll();
}
