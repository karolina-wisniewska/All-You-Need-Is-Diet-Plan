package pl.coderslab.allyouneedisdietplan.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.allyouneedisdietplan.entity.ActivityLevel;
import pl.coderslab.allyouneedisdietplan.entity.CuisineType;
import pl.coderslab.allyouneedisdietplan.entity.Diet;
import pl.coderslab.allyouneedisdietplan.entity.Gender;
import pl.coderslab.allyouneedisdietplan.entity.Health;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.repository.GenderRepository;
import pl.coderslab.allyouneedisdietplan.service.ActivityLevelService;
import pl.coderslab.allyouneedisdietplan.service.CuisineTypeService;
import pl.coderslab.allyouneedisdietplan.service.DietService;
import pl.coderslab.allyouneedisdietplan.service.GenderService;
import pl.coderslab.allyouneedisdietplan.service.HealthService;
import pl.coderslab.allyouneedisdietplan.service.UserDetailsService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {
  private final GenderService genderService;
  private final ActivityLevelService activityLevelService;
  private final CuisineTypeService cuisineTypeService;
  private final HealthService healthService;
  private final DietService dietService;
  private final UserService userService;
  private final UserDetailsService userDetailsService;

  @GetMapping(value = "/user/home")
  public String home() {
    return "user/home";
  }
  @GetMapping(value = "/user/details")
  public String showAddUserDetailsForm(Model model) {
    model.addAttribute("userDetails", new UserDetails());
    model.addAttribute("latestWeight", new LatestWeight());
    return "user/addUserDetails";
  }
  @PostMapping(value = "/user/details")
  public String processAddUserDetailsForm(@Valid UserDetails userDetails, BindingResult userDetailsResult, @Valid LatestWeight latestWeight, BindingResult latestWeightResult, Principal principal) {
    if(userDetailsResult.hasErrors() || latestWeightResult.hasErrors()){
      return "user/addUserDetails";
    }
    userDetails.setUser(userService.findUserByUserName(principal.getName()));
    userDetailsService.save(userDetails);
    latestWeight.setWeightingDate(LocalDateTime.now());
    latestWeight.setUser(userService.findUserByUserName(principal.getName()));
    return "redirect:/user/home";
  }

  @ModelAttribute("genders")
  List<Gender> genders() {
    return genderService.findAll();
  }
  @ModelAttribute("activityLevels")
  List<ActivityLevel> activityLevels() {
    return activityLevelService.findAll();
  }

  @ModelAttribute("cuisineTypes")
  List<CuisineType> cuisineTypes() {
    return cuisineTypeService.findAll();
  }
  @ModelAttribute("healths")
  List<Health> healths() {
    return healthService.findAll();
  }
  @ModelAttribute("diets")
  List<Diet> diets() {
    return dietService.findAll();
  }
}
