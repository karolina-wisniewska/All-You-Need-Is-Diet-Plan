package pl.coderslab.allyouneedisdietplan.repository.dictionary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.ActivityLevel;

@Repository
public interface ActivityLevelRepository extends JpaRepository<ActivityLevel, Integer> {
}
