package mjzguru.com.springframework.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import mjzguru.com.springframework.recipe.commands.RecipeCommand;
import mjzguru.com.springframework.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){ // @PathVariable mathces the variables in url with the method params

        //model.addAttribute("recipe", recipeService.findById(new Long(id)));
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));

        return "recipe/show";
    }

    @GetMapping
    //adding the view capability for data entry recipe form
    @RequestMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    // by adding this method we can populate the recipe form with requested data and then update it with next method
    @GetMapping
    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/recipeform";
    }

    //adding the post back
    //@RequestMapping(name = "recipe", method = RequestMethod.POST)
    @PostMapping
    @RequestMapping( "recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        // ModelAttribute tells spring to bind form post parameters to RecipeCommand object and
        //it will happen automatically by the naming convention of props that we did in the form
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        //redirect: tells Spring MVC to redirect to a url
        return "redirect:/recipe/" + savedCommand.getId() +"/show";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id){

        log.debug("Deleting id: " + id);

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/"; // redirect to root
    }
}
