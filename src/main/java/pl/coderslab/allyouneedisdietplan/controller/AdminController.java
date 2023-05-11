package pl.coderslab.allyouneedisdietplan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class AdminController {

  @GetMapping(value = "/admin/call")
  public String home(Model model, Principal principal) {
    model.addAttribute("userName", principal.getName());
    return "admin/call";
  }
}
