package pl.coderslab.allyouneedisdietplan.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.UserParams;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.LatestWeightDto;
import pl.coderslab.allyouneedisdietplan.model.UserParamsDto;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;
import pl.coderslab.allyouneedisdietplan.service.PlanService;
import pl.coderslab.allyouneedisdietplan.service.UserParamsService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class HomeController {
  private final UserParamsService userParamsService;
  private final UserService userService;
  private final LatestWeightService latestWeightService;
  private final PlanService planService;
  private final ModelMapper modelMapper;

  @GetMapping(value = "/user/home")
  public String home(Model model, Principal principal) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
      return "redirect:/admin/call";
    }

    String userName = principal.getName();
    User currentUser = userService.findUserByUserName(userName);
    UserParams userParams = userParamsService.findByUser(currentUser);
    UserParamsDto userParamsDto = null;

    if (userParams != null) {
      userParamsDto = modelMapper.map(userParams, UserParamsDto.class);
      userParamsDto.setDailyCalories(userParamsService.calculateDailyCalories(userParams));

      LatestWeight latestWeight = latestWeightService.findFirstByUserOrderByWeightingDateDesc(currentUser);
      LatestWeightDto latestWeightDto = modelMapper.map(latestWeight, LatestWeightDto.class);
      model.addAttribute("latestWeightDto", latestWeightDto);

      model.addAttribute("successDate", userParamsService.getSuccessDateMessage(currentUser.getUserParams()));
      model.addAttribute("isPlanComplete", planService.isPlanComplete(planService.findByUser(currentUser)));
    }
    model.addAttribute("userParamsDto", userParamsDto);
    return "home/home";
  }
}
