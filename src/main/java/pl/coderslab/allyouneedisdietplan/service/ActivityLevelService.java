package pl.coderslab.allyouneedisdietplan.service;

import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.ActivityLevel;

import java.util.List;

public interface ActivityLevelService {
  List<ActivityLevel> findAll();

}
