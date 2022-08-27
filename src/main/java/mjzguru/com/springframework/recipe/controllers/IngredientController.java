package mjzguru.com.springframework.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import mjzguru.com.springframework.recipe.commands.IngredientCommand;
import mjzguru.com.springframework.recipe.commands.RecipeCommand;
import mjzguru.com.springframework.recipe.commands.UnitOfMeasureCommand;
import mjzguru.com.springframework.recipe.services.IngredientService;
import mjzguru.com.springframework.recipe.services.RecipeService;
import mjzguru.com.springframework.recipe.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    //Handling Validation and BindingResult manually
    private WebDataBinder webDataBinder;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @InitBinder("ingredient") // since we have multiple databinders (we get a databinder for each of the properties) on the form, we need to specify explicitly here, on what model the databinder is going to be applied
    public void initBinder(WebDataBinder webDataBinder){
        this.webDataBinder = webDataBinder;
    }

//    @GetMapping
//    @RequestMapping("/recipe/{recipeId}/ingredients")
    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {

        log.debug("Getting ingredients list for Recipe Id: " + recipeId);

        // use command object to avoid lazy load errors in Thymeleaf.
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));

        return "recipe/ingredient/list";
    }

//    @GetMapping
//    @RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
    @GetMapping("recipe/{recipeId}/ingredient/{id}/show")
    public  String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model ) {

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));

        return "recipe/ingredient/show";
    }

    // page for adding new ingredient
//    @GetMapping
//    @RequestMapping("recipe/{recipeId}/ingredient/new")
    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newRecipe(@PathVariable String recipeId, Model model){

        // first we need to get the id of the recipe we want to add ingredient to
        //make sure we have a good id value
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId).block();
        //todo raise exception if null

        // since the new ingredient is a detail of recipe, so we need to set the recipe id to this ingredient as Foreign Key
        //need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        //ingredientCommand.setRecipeId(recipeId); // since we do not have any relation in NoSQL dbs we need to comment this part
        // Although I think we should have it, since we need to have the recipe Id when we want to add A NEW INGREDIENT, now the app has a bug in saving new ingredients
        model.addAttribute("ingredient", ingredientCommand); // after initiation of new ingredient we need to give it to the model

        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("uomList",  unitOfMeasureService.listAllUoms()); // we need the list of UOMs to show in the combo box

        return "recipe/ingredient/ingredientform";
    }

//    @GetMapping
//    @RequestMapping("recipe/{recipeId}/ingredient/{id}/update")
    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    // we are displaying the ingredient and now we need to get the Ingredient Command object from the form and need to save that back (persist it)
//    @PostMapping
//    @RequestMapping("recipe/{recipeId}/ingredient")
    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute("ingredient") IngredientCommand command, @PathVariable String recipeId, Model model){ // each of the properties "command" and "RecipeId" will get a databinder, so we need to specify which one we are going to use

        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();

        if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

            return "recipe/ingredient/ingredientform";
        }

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        //log.debug("saved receipe id:" + savedCommand.getRecipeId()); // since we do not have any relation in NoSQL dbs we need to comment this part
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

//    @GetMapping
//    @RequestMapping("recipe/{recipeId}/ingredient/{id}/delete")
    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id){

        log.debug("Deleting Ingredient Id:" + id);

        ingredientService.deleteById(recipeId, id).block();
        return "redirect:/recipe/"+ recipeId + "/ingredients";

    }
}
