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
        String returnValue = "index";

        assertEquals(indexController.getIndexPage(model), returnValue);

        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute("recipes", recipeService.getRecipes());

    }
}