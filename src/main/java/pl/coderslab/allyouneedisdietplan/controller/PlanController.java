package pl.coderslab.allyouneedisdietplan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.Recipe;
import pl.coderslab.allyouneedisdietplan.entity.UserDetails;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.RecipeQuery;
import pl.coderslab.allyouneedisdietplan.model.RecipeResource;
import pl.coderslab.allyouneedisdietplan.model.RecipeResourceList;
import pl.coderslab.allyouneedisdietplan.service.CuisineTypeService;
import pl.coderslab.allyouneedisdietplan.service.DayNameService;
import pl.coderslab.allyouneedisdietplan.service.DietPlanItemService;
import pl.coderslab.allyouneedisdietplan.service.DishTypeService;
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
  private final CuisineTypeService cuisineTypeService;
  private final DishTypeService dishTypeService;

  @GetMapping(value = "/user/plan/new")
  public String getRecipesForPlan(Model model, Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    List<List<DietPlanItem>> resultItems = planService.getDietPlanItemsForPlan(currentUser);
    if(resultItems.isEmpty()){
      model.addAttribute("detailsError", true);
      return "plan/error";
    }
    model.addAttribute("resultItems", resultItems);
    return "plan/show";
  }

  @GetMapping(value = "/user/plan/load")
  public String loadPlan(Model model, Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    List<List<DietPlanItem>> resultItems = planService.loadDietPlanItemsForPlan(currentUser);
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

  @GetMapping(value = "/user/plan/reload")
  public String reloadSingleRecipe(@RequestParam Long id, Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    DietPlanItem itemToEdit = dietPlanItemService.findById(id);
    UserDetails currentUserDetails = userDetailsService.findByUser(currentUser);

    String url = planService.getUserUrl(itemToEdit.getMealType(), currentUserDetails);
    RestTemplate restTemplate = new RestTemplate();
    RecipeResourceList response = restTemplate.getForObject(url, RecipeResourceList.class);
    List<RecipeResource> recipes = response.getHits();

    Recipe recipe = new Recipe();
    recipe.setLabel(recipes.get(0).getRecipe().getLabel());
    recipe.setExternalLink(recipes.get(0).get_links().getSelf().getHref());
    Recipe finalRecipe = recipeService.save(recipe);

    itemToEdit.setRecipe(finalRecipe);
    dietPlanItemService.save(itemToEdit);
    return "redirect:load";
  }

  @GetMapping(value = "/user/plan/choose")
  public String showSingleRecipeForm(Model model, @RequestParam Long id, @RequestParam Integer mealId) {
    RecipeQuery recipeQuery = new RecipeQuery();
    recipeQuery.setDietPlanItem(dietPlanItemService.findById(id));
    recipeQuery.setMealType(mealTypeService.findById(mealId));

    model.addAttribute("recipeQuery", recipeQuery);
    model.addAttribute("cuisineTypes", cuisineTypeService.findAll());
    model.addAttribute("dishTypes", dishTypeService.findAll());
    model.addAttribute("mealTypes", mealTypeService.findAll());
    return "plan/choose";
  }

  @PostMapping(value = "/user/plan/choose")
  public String processSingleRecipeForm(RecipeQuery recipeQuery, Principal principal, Model model) {
    DietPlanItem itemToEdit = recipeQuery.getDietPlanItem();
    User currentUser = userService.findUserByUserName(principal.getName());
    UserDetails currentUserDetails = userDetailsService.findByUser(currentUser);
    String url = planService.getSingleUrl(recipeQuery, currentUserDetails);
    RestTemplate restTemplate = new RestTemplate();
    RecipeResourceList response = restTemplate.getForObject(url, RecipeResourceList.class);
    List<RecipeResource> recipes = response.getHits();
    if(recipes.isEmpty()){
      model.addAttribute("chooseError", true);
      model.addAttribute("recipeQuery", recipeQuery);
      return "plan/error";
    }

    Recipe recipe = new Recipe();
    recipe.setLabel(recipes.get(0).getRecipe().getLabel());
    recipe.setExternalLink(recipes.get(0).get_links().getSelf().getHref());
    Recipe finalRecipe = recipeService.save(recipe);

    itemToEdit.setRecipe(finalRecipe);
    dietPlanItemService.save(itemToEdit);
    return "redirect:load";
  }
}
