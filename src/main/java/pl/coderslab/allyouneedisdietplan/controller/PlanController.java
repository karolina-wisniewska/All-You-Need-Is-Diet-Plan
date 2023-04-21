package pl.coderslab.allyouneedisdietplan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.allyouneedisdietplan.entity.DayName;
import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.MealType;
import pl.coderslab.allyouneedisdietplan.entity.Plan;
import pl.coderslab.allyouneedisdietplan.entity.Recipe;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.RecipeResource;
import pl.coderslab.allyouneedisdietplan.model.RecipeResourceList;
import pl.coderslab.allyouneedisdietplan.service.DayNameService;
import pl.coderslab.allyouneedisdietplan.service.DietPlanItemService;
import pl.coderslab.allyouneedisdietplan.service.MealTypeService;
import pl.coderslab.allyouneedisdietplan.service.PlanService;
import pl.coderslab.allyouneedisdietplan.service.RecipeService;
import pl.coderslab.allyouneedisdietplan.service.UserDetailsService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class PlanController {
  private final UserService userService;
  private final PlanService planService;
  private final UserDetailsService userDetailsService;
  private final MealTypeService mealTypeService;
  private final RecipeService recipeService;
  private final DayNameService dayNameService;
  private final DietPlanItemService dietPlanItemService;

  @GetMapping(value = "/user/plan/new")
  public String getRecipesForPlan(Model model, Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    Plan plan = new Plan();
    plan.setUser(currentUser);
    planService.save(plan);

    UserDetails currentUserDetails = userDetailsService.findByUser(currentUser);
    List<MealType> mealTypes = mealTypeService.findAll();
    List<List<DietPlanItem>> resultItems = new ArrayList<>();

    for(Integer j=0; j <mealTypes.size(); j++){
      MealType mealType = mealTypes.get(j);
      String url = planService.getRequestUrl(mealType, currentUserDetails);
      RestTemplate restTemplate = new RestTemplate();
      RecipeResourceList response = restTemplate.getForObject(url, RecipeResourceList.class);
      List<RecipeResource> recipes = response.getHits();

      List<DietPlanItem> resultItemsPerMeal = new ArrayList<>();
      for(Integer i=0; i <=6; i++){
        Recipe recipe = new Recipe();
        recipe.setLabel(recipes.get(i).getRecipe().getLabel());
        recipe.setExternalLink(recipes.get(i).get_links().getSelf().getHref());
        recipeService.save(recipe);

        DayName dayName = dayNameService.findById(i+1);

        DietPlanItem dietPlanItem = new DietPlanItem();
        dietPlanItem.setPlan(plan);
        dietPlanItem.setMealType(mealType);
        dietPlanItem.setRecipe(recipe);
        dietPlanItem.setDayName(dayName);
        dietPlanItemService.save(dietPlanItem);

        resultItemsPerMeal.add(dietPlanItem);
      }
      resultItems.add(resultItemsPerMeal);
    }
    model.addAttribute("resultItems", resultItems);
    return "plan/show";
  }

  @PostMapping(value = "/user/plan/showDetails")
  public String getSingleRecipe(@RequestParam String url) {
    RestTemplate restTemplate = new RestTemplate();
    RecipeResource recipe = restTemplate.getForObject(url, RecipeResource.class);
    String redirectUrl = recipe.getRecipe().getShareAs();
    return "redirect:" + redirectUrl;
  }

  @PostMapping(value = "/user/plan/reload")
  public String reloadSingleRecipe() {
    return "plan/show";
  }
}
