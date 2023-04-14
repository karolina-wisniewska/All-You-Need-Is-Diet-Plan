package pl.coderslab.allyouneedisdietplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

}
