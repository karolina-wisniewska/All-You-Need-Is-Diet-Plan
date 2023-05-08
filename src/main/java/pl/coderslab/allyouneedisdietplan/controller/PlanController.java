package pl.coderslab.allyouneedisdietplan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.RecipeQueryDto;
import pl.coderslab.allyouneedisdietplan.model.json.RecipeResourceDto;
import pl.coderslab.allyouneedisdietplan.service.CuisineTypeService;
import pl.coderslab.allyouneedisdietplan.service.DayNameService;
import pl.coderslab.allyouneedisdietplan.service.DietPlanItemService;
import pl.coderslab.allyouneedisdietplan.service.DishTypeService;
import pl.coderslab.allyouneedisdietplan.service.MealTypeService;
import pl.coderslab.allyouneedisdietplan.service.PlanService;
import pl.coderslab.allyouneedisdietplan.service.ProviderService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class PlanController {
  private final UserService userService;
  private final PlanService planService;
  private final MealTypeService mealTypeService;
  private final DietPlanItemService dietPlanItemService;
  private final CuisineTypeService cuisineTypeService;
  private final DishTypeService dishTypeService;
  private final DayNameService dayNameService;
  private final ProviderService providerService;

  @GetMapping(value = "/user/plan/new")
  public String getRecipesForPlan(Model model, Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    List<List<DietPlanItem>> resultItems = planService.getDietPlanItemsForPlan(currentUser);
    if(resultItems.isEmpty()){
      model.addAttribute("detailsError", true);
      return "plan/error";
    }
    model.addAttribute("resultItems", resultItems);
    model.addAttribute("dayNames", dayNameService.findAll());
    return "plan/show";
  }

  @GetMapping(value = "/user/plan/load")
  public String loadPlan(Model model, Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    List<List<DietPlanItem>> resultItems = planService.loadDietPlanItemsForPlan(currentUser);
    model.addAttribute("resultItems", resultItems);
    model.addAttribute("dayNames", dayNameService.findAll());
    return "plan/show";
  }

  @PostMapping(value = "/user/plan/showDetails")
  public String getSingleRecipe(@RequestParam String url) {
    return "redirect:" + providerService.getUrlToShowRecipeDetails(url);
  }

  @GetMapping(value = "/user/plan/reload")
  public String reloadSingleRecipe(@RequestParam Long id, Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    DietPlanItem itemToEdit = dietPlanItemService.findById(id);
    List<RecipeResourceDto> recipeResources = planService.getRecipesPerMealType(itemToEdit.getMealType(), currentUser);
    planService.replaceRecipeInDietPlanItem(recipeResources, itemToEdit);
    return "redirect:load";
  }

  @GetMapping(value = "/user/plan/choose")
  public String showSingleRecipeForm(Model model, @RequestParam Long id, @RequestParam Integer mealId) {
    RecipeQueryDto recipeQuery = new RecipeQueryDto();
    recipeQuery.setDietPlanItem(dietPlanItemService.findById(id));
    recipeQuery.setMealType(mealTypeService.findById(mealId));

    model.addAttribute("recipeQuery", recipeQuery);
    model.addAttribute("cuisineTypes", cuisineTypeService.findAll());
    model.addAttribute("dishTypes", dishTypeService.findAll());
    model.addAttribute("mealTypes", mealTypeService.findAll());
    return "plan/choose";
  }

  @PostMapping(value = "/user/plan/choose")
  public String processSingleRecipeForm(RecipeQueryDto recipeQuery, Principal principal, Model model) {
    DietPlanItem itemToEdit = recipeQuery.getDietPlanItem();
    User currentUser = userService.findUserByUserName(principal.getName());
    List<RecipeResourceDto> recipes = planService.getRecipesForRecipeQuery(recipeQuery, currentUser);

    if(recipes.isEmpty()){
      model.addAttribute("chooseError", true);
      model.addAttribute("recipeQuery", recipeQuery);
      return "plan/error";
    }
    planService.replaceRecipeInDietPlanItem(recipes, itemToEdit);
    return "redirect:load";
  }
}
