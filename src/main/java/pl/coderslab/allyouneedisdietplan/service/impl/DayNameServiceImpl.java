package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.DayName;
import pl.coderslab.allyouneedisdietplan.repository.DayNameRepository;
import pl.coderslab.allyouneedisdietplan.service.DayNameService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DayNameServiceImpl implements DayNameService {
  private final DayNameRepository dayNameRepository;

  @Override
  public DayName findById(Integer id) {
    return dayNameRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public List<DayName> findAll() {
    return dayNameRepository.findAll();
  }

  @Override
  public long count() {
    return dayNameRepository.count();
  }
}
