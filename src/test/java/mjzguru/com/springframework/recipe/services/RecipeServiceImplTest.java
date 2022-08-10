package mjzguru.com.springframework.recipe.services;

import mjzguru.com.springframework.recipe.commands.RecipeCommand;
import mjzguru.com.springframework.recipe.converters.RecipeCommandToRecipe;
import mjzguru.com.springframework.recipe.converters.RecipeToRecipeCommand;
import mjzguru.com.springframework.recipe.domain.Recipe;
import mjzguru.com.springframework.recipe.exceptions.NotFoundException;
import mjzguru.com.springframework.recipe.repositories.RecipeRepository;


import mjzguru.com.springframework.recipe.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    // since we don't need and want to Test RecipeRepository directly we inject it as a Mock in our Test by using @Mock
    @Mock
    //RecipeRepository recipeRepository;
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);  // uses injected Mock, tells Mockito to Get me a RecipeRepository

        recipeService = new RecipeServiceImpl(recipeReactiveRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId("1");
        //Optional<Recipe> recipeOptional = Optional.of(recipe);

       // when(recipeReactiveRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        Recipe recipeReturned = recipeService.findById("1").block();

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, never()).findAll();
    }

    /*
    @Test(expected = NotFoundException.class) // expects calling the NotFoundException when the recipe not found (if the optional comes back empty)
    public void getRecipeByIdTestNotFound() throws Exception {
        Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeReactiveRepository.findById(anyString())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById("1");
    }
     */

    @Test
    public void getRecipesTest() throws Exception {
        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);

        //when(recipeService.getRecipes()).thenReturn(recipesData);
        // when the recipeRepository.findAll() called return the content of recipesData instead of original recipeRepository
        //when(recipeReactiveRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeService.getRecipes()).thenReturn(Flux.just(recipe));

        //Set<Recipe> recipes = recipeService.getRecipes();
        List<Recipe> recipes = recipeService.getRecipes().collectList().block();

        assertEquals(recipes.size(), 1);

        verify(recipeReactiveRepository, times(1)).findAll(); // make sure that recipeRepository.findAll() method only called once
        verify(recipeReactiveRepository, never()).findById(anyString());
    }

    @Test
    public void getRecipeCommandByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId("1");
        //Optional<Recipe> recipeOptional = Optional.of(recipe);

        //when(recipeReactiveRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeService.findCommandById("1").block();

        assertNotNull("Null recipe returned", commandById);
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, never()).findAll();
    }

    @Test
    public void deleteByIdTest() throws Exception {
        //given, when, then are based on behaviour driven development
        //given
        String idToDelete = "2";

        when(recipeReactiveRepository.deleteById(anyString())).thenReturn(Mono.empty());

        //when
        recipeService.deleteById(idToDelete);

        //since the deleteById method has void return type we don't need 'when'

        //then
        verify(recipeReactiveRepository, times(1)).deleteById(anyString());
    }
}