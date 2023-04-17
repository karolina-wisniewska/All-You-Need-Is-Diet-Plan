package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.Gender;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.repository.GenderRepository;
import pl.coderslab.allyouneedisdietplan.repository.LatestWeightRepository;
import pl.coderslab.allyouneedisdietplan.service.GenderService;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LatestWeightServiceImpl implements LatestWeightService {

  private final LatestWeightRepository latestWeightRepository;

  @Override
  public List<LatestWeight> findAll() {
    return latestWeightRepository.findAll();
  }

  @Override
  public void save(LatestWeight latestWeight) {
    latestWeightRepository.save(latestWeight);
  }

  @Override
  public LatestWeight findFirstByUserOrderByIdDesc(User user) {
    return latestWeightRepository.findFirstByUserOrderByIdDesc(user);
  }

}
