package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.Gender;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

import java.util.List;

public interface LatestWeightService {

  List<LatestWeight> findAll();

  void save(LatestWeight latestWeight);

  LatestWeight findFirstByUserOrderByIdDesc(User user);

}
