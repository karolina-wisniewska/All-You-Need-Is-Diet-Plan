package pl.coderslab.allyouneedisdietplan.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mysql.cj.xdevapi.JsonArray;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.repository.LatestWeightRepository;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class HighChartController {

  private final UserService userService;
  private final LatestWeightService latestWeightService;

  @GetMapping(value = "/get-data")
  @ResponseBody
  public String getDataFromDB(Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    List<LatestWeight> userWeights = latestWeightService.findByUser(currentUser);
    JSONArray jsonDate = new JSONArray();
    JSONArray jsonWeight = new JSONArray();
    JSONObject json = new JSONObject();
    userWeights.forEach(weight->{
      jsonDate.put(weight.getWeightingDate());
      jsonWeight.put(weight.getWeight());
    });
    json.put("date", jsonDate);
    json.put("weight", jsonWeight);
    return json.toString();
  }

}
