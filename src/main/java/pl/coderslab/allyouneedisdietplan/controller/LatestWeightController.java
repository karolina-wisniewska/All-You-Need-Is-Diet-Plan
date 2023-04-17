package pl.coderslab.allyouneedisdietplan.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;
import pl.coderslab.allyouneedisdietplan.service.UserDetailsService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class LatestWeightController {


  @GetMapping(value = "/user/weight")
  public String showAddLatestWeightForm(Model model) {
    model.addAttribute("latestWeight", new LatestWeight());
    return "weight/add";
  }

  @PostMapping(value = "/user/weight")
  public String processAddLatestWeightForm(@Valid LatestWeight latestWeight, BindingResult result) {
    if(result.hasErrors()){
      return "weight/add";
    }
    return "weight/add";
  }
}
