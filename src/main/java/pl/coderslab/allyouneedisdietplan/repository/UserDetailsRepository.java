package pl.coderslab.allyouneedisdietplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

  UserDetails findByUser(User user);
}
