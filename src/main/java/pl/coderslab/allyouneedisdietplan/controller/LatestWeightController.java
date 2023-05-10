package pl.coderslab.allyouneedisdietplan.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.LatestWeightDto;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class LatestWeightController {
  private final UserService userService;
  private final LatestWeightService latestWeightService;
  private final ModelMapper modelMapper;

  @GetMapping(value = "/user/weight")
  public String showAddLatestWeightForm(Model model) {
    model.addAttribute("latestWeightDto", new LatestWeightDto());
    return "weight/add";
  }

  @PostMapping(value = "/user/weight")
  public String processAddLatestWeightForm(@Valid LatestWeightDto latestWeightDto, BindingResult result, Principal principal) {
    if (result.hasErrors()) {
      return "weight/add";
    }
    User currentUser = userService.findUserByUserName(principal.getName());
    LatestWeight latestWeight = modelMapper.map(latestWeightDto, LatestWeight.class);
    latestWeight.setUser(currentUser);
    latestWeightService.save(latestWeight);
    return "redirect:/user/weight/history";
  }

  @GetMapping(value = "/user/weight/edit")
  public String showEditLatestWeightForm(Model model, @RequestParam Long id) {
    LatestWeight latestWeight = latestWeightService.findById(id);
    LatestWeightDto latestWeightDto = modelMapper.map(latestWeight, LatestWeightDto.class);
    model.addAttribute("latestWeightDto", latestWeightDto);
    return "weight/edit";
  }

  @PostMapping(value = "/user/weight/edit")
  public String processEditLatestWeightForm(@Valid LatestWeightDto latestWeightDto, BindingResult result) {
    if (result.hasErrors()) {
      return "weight/edit";
    }
    LatestWeight latestWeight = modelMapper.map(latestWeightDto, LatestWeight.class);
    latestWeightService.save(latestWeight);
    return "redirect:/user/weight/history";
  }
  @GetMapping(value = "/user/weight/delete")
  public String showEditLatestWeightForm(@RequestParam Long id, Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    latestWeightService.deleteById(id, currentUser);
  return "redirect:/user/weight/history";
  }

  @GetMapping(value = "/user/weight/history")
  public String showWeightHistory(Model model, Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    List<LatestWeight> latestWeightsDesc = latestWeightService.findByUserOrderByWeightingDateDesc(currentUser);
    List<LatestWeightDto> latestWeightDescDto = latestWeightsDesc.stream()
                    .map(latestWeight -> modelMapper.map(latestWeight, LatestWeightDto.class))
                            .collect(Collectors.toList());
    model.addAttribute("latestWeightsDescDto", latestWeightDescDto);
    return "weight/history";
  }


}
