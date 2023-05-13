package pl.coderslab.allyouneedisdietplan.repository.dictionary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
}
