package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.ActivityLevel;
import pl.coderslab.allyouneedisdietplan.entity.Gender;
import pl.coderslab.allyouneedisdietplan.repository.ActivityLevelRepository;
import pl.coderslab.allyouneedisdietplan.repository.GenderRepository;
import pl.coderslab.allyouneedisdietplan.service.ActivityLevelService;
import pl.coderslab.allyouneedisdietplan.service.GenderService;

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
