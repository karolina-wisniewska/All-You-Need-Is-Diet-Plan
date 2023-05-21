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
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.UserParams;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.ActivityLevel;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.CuisineType;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.Diet;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.Health;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.LatestWeightDto;
import pl.coderslab.allyouneedisdietplan.model.UserParamsDto;
import pl.coderslab.allyouneedisdietplan.model.dictionary.ActivityLevelDto;
import pl.coderslab.allyouneedisdietplan.model.dictionary.urlelement.CuisineTypeDto;
import pl.coderslab.allyouneedisdietplan.model.dictionary.urlelement.DietDto;
import pl.coderslab.allyouneedisdietplan.model.dictionary.urlelement.HealthDto;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;
import pl.coderslab.allyouneedisdietplan.service.UserParamsService;
import pl.coderslab.allyouneedisdietplan.service.dictionary.ActivityLevelService;
import pl.coderslab.allyouneedisdietplan.service.dictionary.urlelement.CuisineTypeService;
import pl.coderslab.allyouneedisdietplan.service.dictionary.urlelement.DietService;
import pl.coderslab.allyouneedisdietplan.service.dictionary.urlelement.HealthService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class UserParamsController {
  private final ActivityLevelService activityLevelService;
  private final CuisineTypeService cuisineTypeService;
  private final HealthService healthService;
  private final DietService dietService;
  private final UserService userService;
  private final UserParamsService userParamsService;
  private final LatestWeightService latestWeightService;
  private final ModelMapper modelMapper;

  @GetMapping(value = "/user/params/add")
  public String showAddUserDetailsForm(Model model) {
    model.addAttribute("userParamsDto", new UserParamsDto());

    LatestWeightDto latestWeightDto = new LatestWeightDto();
    latestWeightDto.setWeightingDate(LocalDateTime.now());
    model.addAttribute("latestWeightDto", latestWeightDto);
    return "userParams/add";
  }
  @PostMapping(value = "/user/params/add")
  public String processAddUserDetailsForm(@Valid UserParamsDto userParamsDto, BindingResult userDetailsResult, @Valid LatestWeightDto latestWeightDto, BindingResult latestWeightResult, Principal principal) {
    if(userDetailsResult.hasErrors() || latestWeightResult.hasErrors()){
      return "userParams/add";
    }
    User currentUser = userService.findUserByUserName(principal.getName());
    LatestWeight latestWeight = modelMapper.map(latestWeightDto, LatestWeight.class);
    latestWeight.setUser(currentUser);
    latestWeightService.save(latestWeight);

    UserParams userParams = modelMapper.map(userParamsDto, UserParams.class);
    userParams.setUser(userService.findUserByUserName(principal.getName()));
    userParams.setDailyCalories(userParamsService.calculateDailyCalories(userParams));
    userParamsService.save(userParams);

    return "redirect:/user/home";
  }

  @GetMapping(value = "/user/params/edit")
  public String showEditUserDetailsForm(Model model, Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    UserParams userParams = userParamsService.findByUser(currentUser);
    UserParamsDto userParamsDto = modelMapper.map(userParams, UserParamsDto.class);
    model.addAttribute("userParamsDto", userParamsDto);
    return "userParams/edit";
  }

  @PostMapping(value = "/user/params/edit")
  public String processEditUserDetailsForm(@Valid UserParamsDto userParamsDto, BindingResult userDetailsResult) {
    if(userDetailsResult.hasErrors()){
      return "userParams/edit";
    }
    UserParams userParams = modelMapper.map(userParamsDto, UserParams.class);
    userParams.setDailyCalories(userParamsService.calculateDailyCalories(userParams));
    userParamsService.save(userParams);
    return "redirect:/user/home";
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
