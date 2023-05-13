package pl.coderslab.allyouneedisdietplan.service.dictionary.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Diet;
import pl.coderslab.allyouneedisdietplan.repository.dictionary.DietRepository;
import pl.coderslab.allyouneedisdietplan.service.dictionary.DietService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DietServiceImpl implements DietService {
  private final DietRepository dietRepository;
  @Override
  public List<Diet> findAll(){
    return dietRepository.findAll();
  }

}
