package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.repository.LatestWeightRepository;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LatestWeightServiceImpl implements LatestWeightService {

  private final LatestWeightRepository latestWeightRepository;

  @Override
  public List<LatestWeight> findByUserOrderByWeightingDateAsc(User user) {
    return latestWeightRepository.findByUserOrderByWeightingDateAsc(user);
  }

  @Override
  public List<LatestWeight> findByUserOrderByWeightingDateDesc(User user) {
    return latestWeightRepository.findByUserOrderByWeightingDateDesc(user);
  }

  @Override
  public void save(LatestWeight latestWeight) {
    latestWeightRepository.save(latestWeight);
  }

  @Override
  public LatestWeight findFirstByUserOrderByWeightingDateDesc(User user) {
    return latestWeightRepository.findFirstByUserOrderByWeightingDateDesc(user);
  }

  @Override
  public LatestWeight findById(Long id) {
    return latestWeightRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public void deleteById(Long id, User user) {
    if(latestWeightRepository.countByUser(user) > 1){
    latestWeightRepository.deleteById(id);
    }
  }

}
