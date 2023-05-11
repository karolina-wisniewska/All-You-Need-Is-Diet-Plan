package pl.coderslab.allyouneedisdietplan.service.security.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.security.Role;
import pl.coderslab.allyouneedisdietplan.repository.security.RoleRepository;
import pl.coderslab.allyouneedisdietplan.service.security.RoleService;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;

  @Override
  public Role findByName(String name) {
    return roleRepository.findByName(name);
  }
}
