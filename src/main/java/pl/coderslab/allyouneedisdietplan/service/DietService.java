package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.Diet;
import pl.coderslab.allyouneedisdietplan.entity.Health;

import java.util.List;

public interface DietService {
  List<Diet> findAll();
}
