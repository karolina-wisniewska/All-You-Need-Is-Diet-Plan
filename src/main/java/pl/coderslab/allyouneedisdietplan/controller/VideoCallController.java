package pl.coderslab.allyouneedisdietplan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class VideoCallController {

  @GetMapping(value = "/user/call/test")
  public String showTestVideoCall() {
    return "videoCall/index";
  }

  @GetMapping(value = "/user/call")
  public String showVideoCall(Model model, Principal principal) {
    model.addAttribute("userName", principal.getName());
    return "videoCall/call";
  }
}
