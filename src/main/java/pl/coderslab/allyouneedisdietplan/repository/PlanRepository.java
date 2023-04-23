package pl.coderslab.allyouneedisdietplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
  Plan findByUser(User user);
}
