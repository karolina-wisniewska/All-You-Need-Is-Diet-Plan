package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.repository.DietPlanItemRepository;
import pl.coderslab.allyouneedisdietplan.service.DietPlanItemService;

@Service
@Transactional
@RequiredArgsConstructor
public class DietPlanItemServiceImpl implements DietPlanItemService {
  private final DietPlanItemRepository dietPlanItemRepository;

  @Override
  public void save(DietPlanItem dietPlanItem) {
    dietPlanItemRepository.save(dietPlanItem);
  }
}
