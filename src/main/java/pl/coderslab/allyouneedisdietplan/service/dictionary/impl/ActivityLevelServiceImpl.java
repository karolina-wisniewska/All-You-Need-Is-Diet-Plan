package pl.coderslab.allyouneedisdietplan.service.dictionary.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.ActivityLevel;
import pl.coderslab.allyouneedisdietplan.repository.dictionary.ActivityLevelRepository;
import pl.coderslab.allyouneedisdietplan.service.dictionary.ActivityLevelService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityLevelServiceImpl implements ActivityLevelService {

  private final ActivityLevelRepository activityLevelRepository;
  @Override
  public List<ActivityLevel> findAll(){
    return activityLevelRepository.findAll();
  }

}
