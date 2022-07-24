package mjzguru.com.springframework.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import mjzguru.com.springframework.recipe.commands.RecipeCommand;
import mjzguru.com.springframework.recipe.exceptions.NotFoundException;
import mjzguru.com.springframework.recipe.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {

    private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    //@GetMapping
    //@RequestMapping("/recipe/{id}/show") // we do not need to use both GetMapping and @RequestMapping we can combine them in one annotation
    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){ // @PathVariable mathces the variables in url with the method params

        //model.addAttribute("recipe", recipeService.findById(new Long(id)));
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));

        return "recipe/show";
    }

    //adding the view capability for data entry recipe form
    //@GetMapping
    //@RequestMapping("/recipe/new")
    @GetMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return RECIPE_RECIPEFORM_URL;
    }

    // by adding this method we can populate the recipe form with requested data and then update it with next method
    //@GetMapping
    //@RequestMapping("recipe/{id}/update")
    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return RECIPE_RECIPEFORM_URL;
    }

    //adding the post back
    //@RequestMapping(name = "recipe", method = RequestMethod.POST)
//    @PostMapping
//    @RequestMapping( "recipe")
    @PostMapping( "recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){
        // ModelAttribute tells spring to bind form post parameters to RecipeCommand object and
        //it will happen automatically by the naming convention of props that we did in the form
        // also another important thing is that the BindingResult is tied to the #fields property in thymeleaf in html page (view)
        // and Thymeleaf and spring are integrated this way (for error/validation handling)

        if(bindingResult.hasErrors()){ // added to handle validation messages a
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return RECIPE_RECIPEFORM_URL;
        }
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        //redirect: tells Spring MVC to redirect to a url
        return "redirect:/recipe/" + savedCommand.getId() +"/show";
    }

//    @GetMapping
//    @RequestMapping("recipe/{id}/delete")
    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id){

        log.debug("Deleting id: " + id);

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/"; // redirect to root
    }

    @ResponseStatus(HttpStatus.NOT_FOUND) // add this so that this Status code precedences the default status code
    // (set this status code with upper priority otherwise the real status code would be 200 instead of 404)
    @ExceptionHandler(NotFoundException.class) // this annotation works at controller level (can be used with @ResponseStatus for just returning a http status)
    // here we are saying that we are going to use the "NotFoundException" class
    public ModelAndView handleNotFound(Exception exception) { // we can add the Exception argument so that we can pass in the exception object (info) to the class

        log.error("Handling not found exception!");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error"); // should be set according to our html error page so the ThymeLeaf template engine take it and render it
        modelAndView.addObject("exception", exception); // added to display the exception info on the ErrorPage

        return modelAndView;
    }

}
