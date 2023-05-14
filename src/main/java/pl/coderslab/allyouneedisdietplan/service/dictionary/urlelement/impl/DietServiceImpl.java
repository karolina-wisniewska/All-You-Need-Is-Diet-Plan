package pl.coderslab.allyouneedisdietplan.service.dictionary.urlelement.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.Diet;
import pl.coderslab.allyouneedisdietplan.repository.dictionary.urlelement.DietRepository;
import pl.coderslab.allyouneedisdietplan.service.dictionary.urlelement.DietService;

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
