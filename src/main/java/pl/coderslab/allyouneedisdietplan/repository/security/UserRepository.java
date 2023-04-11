package pl.coderslab.allyouneedisdietplan.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findUserByLogin(String login);
}
