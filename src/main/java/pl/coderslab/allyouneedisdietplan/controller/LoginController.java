package pl.coderslab.allyouneedisdietplan.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

@RequiredArgsConstructor
@Controller
public class LoginController {

  private final UserService userService;
  @GetMapping(value={"/", "/login"})
  public String login(){
    return "login";
  }

  @GetMapping(value="/registration")
  public String registration(Model model){
    model.addAttribute("user", new User());
    return "registration";
  }

  @PostMapping(value = "/registration")
  public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
    User userExists = userService.findUserByUserName(user.getUserName());
    if (userExists != null) {
      bindingResult
              .rejectValue("userName", "error.user",
                      "There is already a user registered with the user name provided");
    }
    if (bindingResult.hasErrors()) {
      return "registration";
    } else {
      userService.saveUser(user);
      model.addAttribute("successMessage", "User has been registered successfully");
      model.addAttribute("user", new User());
    }
    return "registration";
  }

  @GetMapping(value="/user/home")
  public String home(Model model){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = userService.findUserByUserName(auth.getName());
    model.addAttribute("userName", "Welcome " + user.getUserName());
    model.addAttribute("userMessage","Content Available Only for Users with User Role");
    return "user/home";
  }
}
