package pl.coderslab.allyouneedisdietplan.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.repository.LatestWeightRepository;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;
import pl.coderslab.allyouneedisdietplan.service.UserDetailsService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class LatestWeightController {

  private final UserService userService;
  private final LatestWeightService latestWeightService;

  @GetMapping(value = "/user/weight")
  public String showAddLatestWeightForm(Model model) {
    model.addAttribute("latestWeight", new LatestWeight());
    return "weight/add";
  }

  @PostMapping(value = "/user/weight")
  public String processAddLatestWeightForm(@Valid LatestWeight latestWeight, BindingResult result, Principal principal) {
    if (result.hasErrors()) {
      return "weight/add";
    }
    User currentUser = userService.findUserByUserName(principal.getName());
    latestWeight.setUser(currentUser);
    latestWeightService.save(latestWeight);
    return "redirect:/user/home";
  }

  @GetMapping(value = "/user/weight/edit")
  public String showEditLatestWeightForm(Model model, @RequestParam Long id) {
    model.addAttribute("latestWeight", latestWeightService.findById(id));
    return "weight/edit";
  }

  @PostMapping(value = "/user/weight/edit")
  public String processEditLatestWeightForm(@Valid LatestWeight latestWeight, BindingResult result) {
    if (result.hasErrors()) {
      return "weight/edit";
    }
    latestWeightService.save(latestWeight);
    return "redirect:/user/home";
  }
}
