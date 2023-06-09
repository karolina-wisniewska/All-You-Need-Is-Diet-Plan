package pl.coderslab.allyouneedisdietplan.controller.external;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.service.external.HighchartService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class HighchartController {
  private final UserService userService;
  private final HighchartService highChartService;
  @GetMapping(value = "/get-data")
  @ResponseBody
  public String getDataFromDB(Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    JSONObject json = highChartService.getWeightDataJson(currentUser);
    return json.toString();
  }

}
