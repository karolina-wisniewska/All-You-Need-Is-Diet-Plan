package pl.coderslab.allyouneedisdietplan.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.CuisineType;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.DayName;
import pl.coderslab.allyouneedisdietplan.entity.DietPlanItem;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.DishType;
import pl.coderslab.allyouneedisdietplan.entity.dictionary.urlelement.MealType;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.model.dictionary.urlelement.CuisineTypeDto;
import pl.coderslab.allyouneedisdietplan.model.dictionary.DayNameDto;
import pl.coderslab.allyouneedisdietplan.model.DietPlanItemDto;
import pl.coderslab.allyouneedisdietplan.model.dictionary.urlelement.DishTypeDto;
import pl.coderslab.allyouneedisdietplan.model.dictionary.urlelement.MealTypeDto;
import pl.coderslab.allyouneedisdietplan.model.RecipeQueryDto;
import pl.coderslab.allyouneedisdietplan.external.edamam.RecipeResourceDto;
import pl.coderslab.allyouneedisdietplan.service.dictionary.urlelement.CuisineTypeService;
import pl.coderslab.allyouneedisdietplan.service.dictionary.DayNameService;
import pl.coderslab.allyouneedisdietplan.service.DietPlanItemService;
import pl.coderslab.allyouneedisdietplan.service.dictionary.urlelement.DishTypeService;
import pl.coderslab.allyouneedisdietplan.service.dictionary.urlelement.MealTypeService;
import pl.coderslab.allyouneedisdietplan.service.PlanService;
import pl.coderslab.allyouneedisdietplan.service.external.EdamamService;
import pl.coderslab.allyouneedisdietplan.service.security.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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
  private final EdamamService edamamService;
  private final ModelMapper modelMapper;

  @GetMapping(value = "/user/plan/new")
  public String getRecipesForPlan(Model model, Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    List<List<DietPlanItem>> resultItems = planService.getDietPlanItemsForPlan(currentUser);
    if (resultItems.isEmpty()) {
      model.addAttribute("detailsError", true);
      return "plan/error";
    }
    List<List<DietPlanItemDto>> resultItemsDto = convertEntityToDtoDietPlanItem(resultItems);
    model.addAttribute("resultItems", resultItemsDto);

    List<DayName> dayNames = dayNameService.findAll();
    List<DayNameDto> dayNameDtos = convertEntityToDtoDayName(dayNames);
    model.addAttribute("dayNames", dayNameDtos);
    return "plan/show";
  }

  @GetMapping(value = "/user/plan/load")
  public String loadPlan(Model model, Principal principal) {
    User currentUser = userService.findUserByUserName(principal.getName());
    List<List<DietPlanItem>> resultItems = planService.loadDietPlanItemsForPlan(currentUser);
    List<List<DietPlanItemDto>> resultItemsDto = convertEntityToDtoDietPlanItem(resultItems);
    model.addAttribute("resultItems", resultItemsDto);

    List<DayName> dayNames = dayNameService.findAll();
    List<DayNameDto> dayNamesDto = convertEntityToDtoDayName(dayNames);
    model.addAttribute("dayNames", dayNamesDto);
    return "plan/show";
  }

  @PostMapping(value = "/user/plan/showDetails")
  public String getSingleRecipe(@RequestParam String url) {
    return "redirect:" + edamamService.getUrlToShowRecipeDetails(url);
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
    RecipeQueryDto recipeQueryDto = new RecipeQueryDto();
    recipeQueryDto.setDietPlanItem(dietPlanItemService.findById(id));
    recipeQueryDto.setMealType(mealTypeService.findById(mealId));
    model.addAttribute("recipeQuery", recipeQueryDto);

    List<CuisineType> cuisineTypes = cuisineTypeService.findAll();
    List<CuisineTypeDto> cuisineTypeDtos = convertEntityToDtoCuisineType(cuisineTypes);
    model.addAttribute("cuisineTypes", cuisineTypeDtos);

    List<DishType> dishTypes = dishTypeService.findAll();
    List<DishTypeDto> dishTypesDto = convertEntityToDtoDishType(dishTypes);
    model.addAttribute("dishTypes", dishTypesDto);

    List<MealType> mealTypes = mealTypeService.findAll();
    List<MealTypeDto> mealTypesDto = convertEntityToMealType(mealTypes);
    model.addAttribute("mealTypes", mealTypesDto);
    return "plan/choose";
  }

  @PostMapping(value = "/user/plan/choose")
  public String processSingleRecipeForm(RecipeQueryDto recipeQueryDto, Principal principal, Model model) {
    DietPlanItem itemToEdit = recipeQueryDto.getDietPlanItem();
    User currentUser = userService.findUserByUserName(principal.getName());
    List<RecipeResourceDto> recipes = planService.getRecipesForRecipeQuery(recipeQueryDto, currentUser);

    if (recipes.isEmpty()) {
      recipeQueryDto.setMealType(itemToEdit.getMealType());
      model.addAttribute("chooseError", true);
      model.addAttribute("recipeQuery", recipeQueryDto);
      return "plan/error";
    }
    planService.replaceRecipeInDietPlanItem(recipes, itemToEdit);
    return "redirect:load";
  }

  private List<List<DietPlanItemDto>> convertEntityToDtoDietPlanItem(List<List<DietPlanItem>> resultItems){
    return resultItems.stream()
            .map(innerList -> innerList.stream()
                    .map(resultItem ->
                            modelMapper.map(resultItem, DietPlanItemDto.class))
                    .collect(Collectors.toList()))
            .collect(Collectors.toList());
  }

  private List<DayNameDto> convertEntityToDtoDayName(List<DayName> dayNames){
    return dayNames.stream()
            .map(dayName -> modelMapper.map(dayName, DayNameDto.class))
            .collect(Collectors.toList());
  }

  private List<CuisineTypeDto> convertEntityToDtoCuisineType(List<CuisineType> cuisineTypes){
    return cuisineTypes.stream()
            .map(cuisineType -> modelMapper.map(cuisineType, CuisineTypeDto.class))
            .collect(Collectors.toList());
  }

  private List<DishTypeDto> convertEntityToDtoDishType(List<DishType> dishTypes){
    return dishTypes.stream()
            .map(dishType -> modelMapper.map(dishType, DishTypeDto.class))
            .collect(Collectors.toList());
  }

  private List<MealTypeDto> convertEntityToMealType(List<MealType> mealTypes){
    return mealTypes.stream()
            .map(mealType -> modelMapper.map(mealType, MealTypeDto.class))
            .collect(Collectors.toList());
  }
}
