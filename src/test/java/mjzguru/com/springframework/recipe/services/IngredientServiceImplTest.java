package mjzguru.com.springframework.recipe.services;

import mjzguru.com.springframework.recipe.commands.IngredientCommand;
import mjzguru.com.springframework.recipe.commands.UnitOfMeasureCommand;
import mjzguru.com.springframework.recipe.converters.IngredientCommandToIngredient;
import mjzguru.com.springframework.recipe.converters.IngredientToIngredientCommand;
import mjzguru.com.springframework.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import mjzguru.com.springframework.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import mjzguru.com.springframework.recipe.domain.Ingredient;
import mjzguru.com.springframework.recipe.domain.Recipe;
import mjzguru.com.springframework.recipe.repositories.RecipeRepository;
import mjzguru.com.springframework.recipe.repositories.reactive.RecipeReactiveRepository;
import mjzguru.com.springframework.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {


    // we do not need to mock converters
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    IngredientService ingredientService;

    //since the converters are not Mocks we need to initiate them in constructor
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                recipeReactiveRepository, recipeRepository, unitOfMeasureReactiveRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientIdTest() {

        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("1");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("2");

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId("3");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "3").block();

        //when
        assertEquals("3", ingredientCommand.getId());
        //assertEquals("1", ingredientCommand.getRecipeId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
    }

    @Test
    public void testSaveRecipeCommand() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("3");
        command.setRecipeId("2");
        command.setUom(new UnitOfMeasureCommand());
        command.getUom().setId("1234");

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("3");

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        //then
        assertEquals("3", savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    public void deleteByIdTest() throws Exception {

        //given
       Recipe recipe = new Recipe();
       Ingredient ingredient = new Ingredient();
       ingredient.setId("3");
       recipe.addIngredient(ingredient);
       //ingredient.setRecipe(recipe); // removed to avoid circular dependency in Mongo docs

       Optional<Recipe> recipeOptional = Optional.of(recipe);

       when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

       //when
        ingredientService.deleteById("1", "3");

        //then
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository,times(1)).save(any(Recipe.class));

    }
}