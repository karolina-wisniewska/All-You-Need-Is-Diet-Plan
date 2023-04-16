package pl.coderslab.allyouneedisdietplan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.service.UserDetailsService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserController {
  private final UserDetailsService userDetailsService;
  private final UserService userService;


  @GetMapping(value = "/user/home")
  public String home(Model model, Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    model.addAttribute("userDetails", userDetailsService.findByUser(currentUser));
    return "user/home";
  }
}
