package pl.coderslab.allyouneedisdietplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.allyouneedisdietplan.entity.UserParams;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

public interface UserParamsRepository extends JpaRepository<UserParams, Long> {

  UserParams findByUser(User user);
}
