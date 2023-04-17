package pl.coderslab.allyouneedisdietplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.allyouneedisdietplan.entity.Gender;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

@Repository
public interface LatestWeightRepository extends JpaRepository<LatestWeight, Long> {

  LatestWeight findFirstByUserOrderByIdDesc(User user);
}
