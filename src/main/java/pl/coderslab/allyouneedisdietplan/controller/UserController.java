package pl.coderslab.allyouneedisdietplan.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.LatestWeightDto;
import pl.coderslab.allyouneedisdietplan.model.UserDetailsDto;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;
import pl.coderslab.allyouneedisdietplan.service.PlanService;
import pl.coderslab.allyouneedisdietplan.service.UserDetailsService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserController {
  private final UserDetailsService userDetailsService;
  private final UserService userService;
  private final LatestWeightService latestWeightService;
  private final PlanService planService;
  private final ModelMapper modelMapper;
  private final String DIETITIAN_USERNAME = "Dietitian";

  @GetMapping(value = "/user/home")
  public String home(Model model, Principal principal) {
    String userName = principal.getName();
    User currentUser = userService.findUserByUserName(userName);
    if(DIETITIAN_USERNAME.equals(userName)){
      return "redirect:/user/call";
    }

    UserDetails userDetails = userDetailsService.findByUser(currentUser);
      UserDetailsDto userDetailsDto = null;

    if (userDetails != null) {
      userDetailsDto = modelMapper.map(userDetails, UserDetailsDto.class);
      userDetails.setDailyCalories(userDetailsService.calculateDailyCalories(userDetails));

      LatestWeight latestWeight = latestWeightService.findFirstByUserOrderByIdDesc(currentUser);
      LatestWeightDto latestWeightDto = modelMapper.map(latestWeight, LatestWeightDto.class);
      model.addAttribute("latestWeight", latestWeightDto);

      model.addAttribute("successDate", userDetailsService.calculateSuccessDate(currentUser.getUserDetails()));
      model.addAttribute("isPlanComplete", planService.isPlanComplete(planService.findByUser(currentUser)));
    }
    model.addAttribute("userDetails", userDetailsDto);
    return "user/home";
  }
}
