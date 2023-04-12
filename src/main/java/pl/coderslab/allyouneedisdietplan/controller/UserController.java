package pl.coderslab.allyouneedisdietplan.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.allyouneedisdietplan.entity.ActivityLevel;
import pl.coderslab.allyouneedisdietplan.entity.Gender;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.repository.GenderRepository;
import pl.coderslab.allyouneedisdietplan.service.ActivityLevelService;
import pl.coderslab.allyouneedisdietplan.service.GenderService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {

  private final UserService userService;
  private final GenderService genderService;
  private final ActivityLevelService activityLevelService;


  @GetMapping(value="/user/home")
  public String home(){
    return "user/home";
  }

  @GetMapping(value="/user/details")
  public String showAddUserDetailsForm(Model model){
    List<Gender> genders = genderService.findAll();
    model.addAttribute("genders", genders);
    model.addAttribute("userDetails", new UserDetails());
    List<ActivityLevel> activityLevels = activityLevelService.findAll();
    model.addAttribute("activityLevels", activityLevels);
    return "user/addUserDetails";
  }


}
