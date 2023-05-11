package pl.coderslab.allyouneedisdietplan.service.security.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.security.Role;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.repository.security.RoleRepository;
import pl.coderslab.allyouneedisdietplan.repository.security.UserRepository;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.util.Arrays;
import java.util.HashSet;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public User findUserByUserName(String userName) {
    return userRepository.findUserByUserName(userName);
  }

  @Override
  public User saveUser(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setActive(true);
    Role userRole = roleRepository.findByName("ROLE_USER");
    user.setRoles(new HashSet<>(Arrays.asList(userRole)));
    return userRepository.save(user);
  }

  public User findByRole(Role role){
    return userRepository.findByRole(role);
  };

}
