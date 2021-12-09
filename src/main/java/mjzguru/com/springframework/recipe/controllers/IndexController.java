package mjzguru.com.springframework.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import mjzguru.com.springframework.recipe.domain.Category;
import mjzguru.com.springframework.recipe.domain.UnitOfMeasure;
import mjzguru.com.springframework.recipe.repositories.CategoryRepository;
import mjzguru.com.springframework.recipe.repositories.RecipeRepository;
import mjzguru.com.springframework.recipe.repositories.UnitOfMeasureRepository;
import mjzguru.com.springframework.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeRepository recipeRepository;
    private final RecipeService recipeService;
    //private RecipeService recipeService;

   /*
   public IndexController(){

    }

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    */

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository, RecipeService recipeService) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
        this.recipeService = recipeService;

        log.debug("I'm in IndexController Constructor!!!!");
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String getIndexPage(Model model){
        log.info("getIndexPage method called!!!!");

        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }

    @RequestMapping({"/indexb", "/indexb.html"})
    public String getIndexBPage(Model model){

        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        System.out.println("Category Id is: " + categoryOptional.get().getId());
        System.out.println("UOM Id is: " + unitOfMeasureOptional.get().getId());

        System.out.println("Some message to say...");

        model.addAttribute("recipes", recipeRepository.findAll());

        return "indexb";
    }
}
