package pl.coderslab.allyouneedisdietplan.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.UserDto;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

@RequiredArgsConstructor
@Controller
public class AuthenticationController {

  public final String successMessage = "User has been registered successfully";
  public final String userError = "There is already a user registered with the user name provided";
  private final UserService userService;
  private final ModelMapper modelMapper;
  @GetMapping(value={"/", "/login"})
  public String login(){
    return "login";
  }


  @GetMapping(value="/registration")
  public String registration(Model model){
    model.addAttribute("userDto", new UserDto());
    return "registration";
  }

  @PostMapping(value = "/registration")
  public String createNewUser(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
    User user = modelMapper.map(userDto, User.class);
    User userExists = userService.findUserByUserName(user.getUserName());
    if (userExists != null) {
      bindingResult
              .rejectValue("userName", "error.user",
                      userError);
    }
    if (bindingResult.hasErrors()) {
      return "registration";
    } else {
      userService.saveUser(user);
      model.addAttribute("successMessage", successMessage);
      model.addAttribute("userDto", new UserDto());
    }
    return "registration";
  }

}
