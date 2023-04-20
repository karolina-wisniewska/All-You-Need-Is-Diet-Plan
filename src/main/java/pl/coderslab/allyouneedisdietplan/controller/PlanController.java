package pl.coderslab.allyouneedisdietplan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.entity.Recipe;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.RecipeResource;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;
import pl.coderslab.allyouneedisdietplan.service.UserDetailsService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class PlanController {
  private final UserDetailsService userDetailsService;
  private final UserService userService;

  @GetMapping(value = "/user/plan/new")
  public String getRecipesForPlan(Model model, Principal principal) {
//    User currentUser = userService.findUserByUserName(principal.getName());
//    Plan plan = new Plan();
//
//    model.addAttribute("plan", plan);
    return "plan/new";
  }

  @GetMapping(value = "/user/plan/showDetails")
  @ResponseBody
  public String getSingleRecipe() {
    String url = "https://api.edamam.com/api/recipes/v2?q=chicken&type=public&app_id=40fa347c&app_key=0eb977d62e50265cf4df0451172393a6";
    RestTemplate restTemplate = new RestTemplate();
    RecipeResource recipe = restTemplate.getForObject(url, RecipeResource.class);
    System.out.println("Recipe: " + recipe);
    return "Recipe details";
  }

  @GetMapping(value = "/user/plan/test")
  @ResponseBody
  public String getListOfRecipes() {
    String url = "https://api.edamam.com/api/recipes/v2?q=chicken&type=public&app_id=40fa347c&app_key=0eb977d62e50265cf4df0451172393a6";
    RestTemplate restTemplate = new RestTemplate();
    RecipeResource recipe = restTemplate.getForObject(url, RecipeResource.class);
    System.out.println("Recipe: " + recipe);
    return "Recipe details";
  }
}
