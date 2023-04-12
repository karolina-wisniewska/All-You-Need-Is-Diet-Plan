package pl.coderslab.allyouneedisdietplan.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.allyouneedisdietplan.entity.Gender;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.repository.GenderRepository;
import pl.coderslab.allyouneedisdietplan.service.GenderService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {

  private final UserService userService;
  private final GenderService genderService;

  @GetMapping(value="/user/home")
  public String home(){
    return "user/home";
  }

  @GetMapping(value="/user/details")
  public String showUserDetailsForm(Model model){
    List<Gender> genders = genderService.findAll();
    model.addAttribute("genders", genders);
    return "user/addUserDetails";
  }


}
