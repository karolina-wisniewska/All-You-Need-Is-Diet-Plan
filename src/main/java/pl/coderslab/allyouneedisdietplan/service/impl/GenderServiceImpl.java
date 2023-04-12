package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.Gender;
import pl.coderslab.allyouneedisdietplan.repository.GenderRepository;
import pl.coderslab.allyouneedisdietplan.service.GenderService;

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
