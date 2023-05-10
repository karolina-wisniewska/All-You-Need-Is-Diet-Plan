package pl.coderslab.allyouneedisdietplan.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
import pl.coderslab.allyouneedisdietplan.model.ActivityLevelDto;
import pl.coderslab.allyouneedisdietplan.model.CuisineTypeDto;
import pl.coderslab.allyouneedisdietplan.model.DietDto;
import pl.coderslab.allyouneedisdietplan.model.GenderDto;
import pl.coderslab.allyouneedisdietplan.model.HealthDto;
import pl.coderslab.allyouneedisdietplan.model.LatestWeightDto;
import pl.coderslab.allyouneedisdietplan.model.UserDetailsDto;
import pl.coderslab.allyouneedisdietplan.service.ActivityLevelService;
import pl.coderslab.allyouneedisdietplan.service.CuisineTypeService;
import pl.coderslab.allyouneedisdietplan.service.DietService;
import pl.coderslab.allyouneedisdietplan.service.GenderService;
import pl.coderslab.allyouneedisdietplan.service.HealthService;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;
import pl.coderslab.allyouneedisdietplan.service.UserDetailsService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class UserDetailsController {
  private final GenderService genderService;
  private final ActivityLevelService activityLevelService;
  private final CuisineTypeService cuisineTypeService;
  private final HealthService healthService;
  private final DietService dietService;
  private final UserService userService;
  private final UserDetailsService userDetailsService;
  private final LatestWeightService latestWeightService;
  private final ModelMapper modelMapper;

  @GetMapping(value = "/user/details/add")
  public String showAddUserDetailsForm(Model model) {
    model.addAttribute("userDetailsDto", new UserDetailsDto());

    LatestWeightDto latestWeightDto = new LatestWeightDto();
    latestWeightDto.setWeightingDate(LocalDateTime.now());
    model.addAttribute("latestWeightDto", latestWeightDto);
    return "userDetails/add";
  }
  @PostMapping(value = "/user/details/add")
  public String processAddUserDetailsForm(@Valid UserDetailsDto userDetailsDto, BindingResult userDetailsResult, @Valid LatestWeightDto latestWeightDto, BindingResult latestWeightResult, Principal principal) {
    if(userDetailsResult.hasErrors() || latestWeightResult.hasErrors()){
      return "userDetails/add";
    }
    User currentUser = userService.findUserByUserName(principal.getName());
    LatestWeight latestWeight = modelMapper.map(latestWeightDto, LatestWeight.class);
    latestWeight.setUser(currentUser);
    latestWeightService.save(latestWeight);

    UserDetails userDetails = modelMapper.map(userDetailsDto, UserDetails.class);
    userDetails.setUser(userService.findUserByUserName(principal.getName()));
    userDetails.setDailyCalories(userDetailsService.calculateDailyCalories(userDetails));
    userDetailsService.save(userDetails);

    return "redirect:/user/home";
  }

  @GetMapping(value = "/user/details/edit")
  public String showEditUserDetailsForm(Model model, Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    UserDetails userDetails = userDetailsService.findByUser(currentUser);
    UserDetailsDto userDetailsDto = modelMapper.map(userDetails, UserDetailsDto.class);
    model.addAttribute("userDetailsDto", userDetailsDto);
    return "userDetails/edit";
  }

  @PostMapping(value = "/user/details/edit")
  public String processEditUserDetailsForm(@Valid UserDetailsDto userDetailsDto, BindingResult userDetailsResult) {
    if(userDetailsResult.hasErrors()){
      return "userDetails/edit";
    }
    UserDetails userDetails = modelMapper.map(userDetailsDto, UserDetails.class);
    userDetails.setDailyCalories(userDetailsService.calculateDailyCalories(userDetails));
    userDetailsService.save(userDetails);
    return "redirect:/user/home";
  }
  @ModelAttribute("gendersDto")
  List<GenderDto> genders() {
    List<Gender> genders = genderService.findAll();
    return genders.stream()
            .map(gender -> modelMapper.map(gender, GenderDto.class))
            .collect(Collectors.toList());
  }
  @ModelAttribute("activityLevelsDto")
  List<ActivityLevelDto> activityLevels() {
    List<ActivityLevel> activityLevels = activityLevelService.findAll();
    return activityLevels.stream()
            .map(activityLevel -> modelMapper.map(activityLevel, ActivityLevelDto.class))
            .collect(Collectors.toList());
  }
  @ModelAttribute("cuisineTypesDto")
  List<CuisineTypeDto> cuisineTypes() {
    List<CuisineType> cuisineTypes = cuisineTypeService.findAll();
    return cuisineTypes.stream()
            .map(cuisineType -> modelMapper.map(cuisineType, CuisineTypeDto.class))
            .collect(Collectors.toList());
  }
  @ModelAttribute("healthsDto")
  List<HealthDto> healths() {
    List<Health> healths = healthService.findAll();
    return healths.stream()
            .map(health -> modelMapper.map(health, HealthDto.class))
            .collect(Collectors.toList());
  }
  @ModelAttribute("dietsDto")
  List<DietDto> diets() {
    List<Diet> diets = dietService.findAll();
    return diets.stream()
            .map(diet -> modelMapper.map(diet, DietDto.class))
            .collect(Collectors.toList());
  }
}
