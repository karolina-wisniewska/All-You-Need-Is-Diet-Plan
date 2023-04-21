package pl.coderslab.allyouneedisdietplan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    for(MealType mealType : mealTypes){
      String url = planService.getRequestUrl(mealType, currentUserDetails);
      RestTemplate restTemplate = new RestTemplate();
      RecipeResourceList response = restTemplate.getForObject(url, RecipeResourceList.class);
      List<RecipeResource> recipes = response.getHits();

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

      }

    }

    model.addAttribute("plan", plan);
    return "plan/new";
  }

  @GetMapping(value = "/user/plan/showDetails")
  @ResponseBody
  public String getSingleRecipe() {
    String url = "https://api.edamam.com/api/recipes/v2/b79327d05b8e5b838ad6cfd9576b30b6?type=public&app_id=40fa347c&app_key=0eb977d62e50265cf4df0451172393a6";
    RestTemplate restTemplate = new RestTemplate();
    RecipeResource recipe = restTemplate.getForObject(url, RecipeResource.class);
    System.out.println("Recipe: " + recipe);
    return "Recipe details";
  }

  @GetMapping(value = "/user/plan/test")
  @ResponseBody
  public String getListOfRecipes(Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    UserDetails currentUserDetails = userDetailsService.findByUser(currentUser);
    List<MealType> mealTypes = mealTypeService.findAll();
    String url = planService.getRequestUrl(mealTypes.get(0), currentUserDetails);
    RestTemplate restTemplate = new RestTemplate();
    RecipeResourceList response = restTemplate.getForObject(url, RecipeResourceList.class);
    List<RecipeResource> recipes = response.getHits();
    return "Recipe list";
  }

  @GetMapping(value = "/user/plan/url")
  @ResponseBody
  public String getMealUrl(Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    UserDetails currentUserDetails = userDetailsService.findByUser(currentUser);
    List<MealType> mealTypes = mealTypeService.findAll();
    String url = planService.getRequestUrl(mealTypes.get(0), currentUserDetails);
    return url;
  }


}
