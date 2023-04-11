package pl.coderslab.allyouneedisdietplan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

@RequiredArgsConstructor
@Controller
public class LoginController {

  private final UserService userService;
  @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
  public String login(){
    return "/user/login";
  }

  @RequestMapping(value="/registration", method = RequestMethod.GET)
  public String registration(Model model){
    model.addAttribute("user", new User());
    return "user/registration";
  }


}
