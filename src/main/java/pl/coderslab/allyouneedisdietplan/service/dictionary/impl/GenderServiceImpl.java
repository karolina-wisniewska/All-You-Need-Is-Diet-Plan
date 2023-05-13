package pl.coderslab.allyouneedisdietplan.service.dictionary.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.Gender;
import pl.coderslab.allyouneedisdietplan.repository.dictionary.GenderRepository;
import pl.coderslab.allyouneedisdietplan.service.dictionary.GenderService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GenderServiceImpl implements GenderService {

  private final GenderRepository genderRepository;
  @Override
  public List<Gender> findAll(){
    return genderRepository.findAll();
  }

}
