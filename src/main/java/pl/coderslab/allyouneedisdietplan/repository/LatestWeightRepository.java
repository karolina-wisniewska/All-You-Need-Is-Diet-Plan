package pl.coderslab.allyouneedisdietplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

import java.util.List;

@Repository
public interface LatestWeightRepository extends JpaRepository<LatestWeight, Long> {

  LatestWeight findFirstByUserOrderByIdDesc(User user);
  List<LatestWeight> findByUserOrderByWeightingDateAsc(User user);
  List<LatestWeight> findByUserOrderByWeightingDateDesc(User user);
  long countByUser(User user);
}
