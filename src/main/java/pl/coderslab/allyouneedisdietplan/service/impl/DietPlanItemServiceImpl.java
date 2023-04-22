package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.repository.DietPlanItemRepository;
import pl.coderslab.allyouneedisdietplan.service.DietPlanItemService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DietPlanItemServiceImpl implements DietPlanItemService {
  private final DietPlanItemRepository dietPlanItemRepository;

  @Override
  public void save(DietPlanItem dietPlanItem) {
    dietPlanItemRepository.save(dietPlanItem);
  }

  @Override
  public List<DietPlanItem> findByPlanOrderByIdAsc(Plan plan) {
    return dietPlanItemRepository.findByPlanOrderByIdAsc(plan);
  }

  @Override
  public DietPlanItem findById(Long id) {
    return dietPlanItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }
}
