package pl.coderslab.allyouneedisdietplan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class VideoCallController {

  @GetMapping(value = "/user/call")
  public String home() {
    return "videoCall/index";
  }
}
