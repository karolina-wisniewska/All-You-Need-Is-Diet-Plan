package pl.coderslab.allyouneedisdietplan.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.allyouneedisdietplan.entity.security.Role;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.UserDto;
import pl.coderslab.allyouneedisdietplan.service.security.RoleService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class VideoCallController {
  private final RoleService roleService;
  private final UserService userService;
  private final ModelMapper modelMapper;
  @GetMapping(value = "/user/call/test")
  public String showTestVideoCall() {
    return "videoCall/index";
  }

  @GetMapping(value = "/user/call")
  public String showVideoCall(Model model, Principal principal) {
    model.addAttribute("userName", principal.getName());
    Role adminRole = roleService.findByName("ROLE_ADMIN");
    User admin = userService.findByRole(adminRole);
    model.addAttribute("adminDto", modelMapper.map(admin, UserDto.class));
    return "videoCall/call";
  }

  @GetMapping(value = "/admin/call")
  public String home(Model model, Principal principal) {
    model.addAttribute("userName", principal.getName());
    return "videoCall/adminCall";
  }

}
