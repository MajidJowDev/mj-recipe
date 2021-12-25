package mjzguru.com.springframework.recipe.controllers;

import mjzguru.com.springframework.recipe.repositories.CategoryRepository;
import mjzguru.com.springframework.recipe.repositories.RecipeRepository;
import mjzguru.com.springframework.recipe.repositories.UnitOfMeasureRepository;
import mjzguru.com.springframework.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class IndexControllerTest {

    IndexController indexController;

    @Mock
    RecipeService recipeService;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Mock
    RecipeRepository recipeRepository;

    @Mock
    Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(categoryRepository, unitOfMeasureRepository, recipeRepository, recipeService );

    }

    @Test
    public void getIndexPage() {
        String viewNameReturnValue = "index";

        //assertEquals(indexController.getIndexPage(model), returnValue);
        assertEquals(viewNameReturnValue,indexController.getIndexPage(model)); // first param = expected, second param = actual

        verify(recipeService, times(1)).getRecipes();
        //verify(model, times(1)).addAttribute("recipes", recipeService.getRecipes());
        // also checks if passed params to addAttribute method are like the following (first param is "recipe" amd second param is some Set or List
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());

    }
}