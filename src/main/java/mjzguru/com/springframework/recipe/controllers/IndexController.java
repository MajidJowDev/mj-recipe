package mjzguru.com.springframework.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import mjzguru.com.springframework.recipe.domain.Category;
import mjzguru.com.springframework.recipe.domain.UnitOfMeasure;
import mjzguru.com.springframework.recipe.repositories.CategoryRepository;
import mjzguru.com.springframework.recipe.repositories.RecipeRepository;
import mjzguru.com.springframework.recipe.repositories.UnitOfMeasureRepository;
import mjzguru.com.springframework.recipe.repositories.reactive.CategoryReactiveRepository;
import mjzguru.com.springframework.recipe.repositories.reactive.RecipeReactiveRepository;
import mjzguru.com.springframework.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import mjzguru.com.springframework.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

    private CategoryReactiveRepository categoryReactiveRepository;
    private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeService recipeService;
    //private RecipeService recipeService;

   /*
   public IndexController(){

    }

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    */

    public IndexController(CategoryReactiveRepository categoryReactiveRepository, UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository, RecipeReactiveRepository recipeReactiveRepository, RecipeService recipeService) {
        this.categoryReactiveRepository = categoryReactiveRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeService = recipeService;

        log.debug("I'm in IndexController Constructor!!!!");
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String getIndexPage(Model model){
        log.info("getIndexPage method called!!!!");

        model.addAttribute("recipes", recipeService.getRecipes().collectList().block());

        return "index";
    }

    @RequestMapping({"/indexb", "/indexb.html"})
    public String getIndexBPage(Model model){

       // Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
       // Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        Category searchedCategoryTst = categoryReactiveRepository.findByDescription("American").block();
        UnitOfMeasure searchedUOM_Tst = unitOfMeasureReactiveRepository.findByDescription("Teaspoon").block();

        System.out.println("Category Id is: " + searchedCategoryTst.getId());
        System.out.println("UOM Id is: " + searchedUOM_Tst.getId());

        System.out.println("Some message to say...");

        //model.addAttribute("recipes", recipeRepository.findAll());
        model.addAttribute("recipes", recipeReactiveRepository.findAll().collectList().block());
       // model.addAttribute("recipes", recipeService.getRecipes().collectList().block());

        return "indexb";
    }
}
