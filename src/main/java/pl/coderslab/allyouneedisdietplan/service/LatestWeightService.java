package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

import java.util.List;

public interface LatestWeightService {

  List<LatestWeight> findByUserOrderByWeightingDateAsc(User user);
  List<LatestWeight> findByUserOrderByWeightingDateDesc(User user);
  void save(LatestWeight latestWeight);
  LatestWeight findFirstByUserOrderByWeightingDateDesc(User user);
  LatestWeight findById(Long id);
  void deleteById(Long id, User user);
  long countByUser(User user);

}
