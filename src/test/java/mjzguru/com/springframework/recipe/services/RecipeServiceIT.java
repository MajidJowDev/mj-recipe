package mjzguru.com.springframework.recipe.services;

import mjzguru.com.springframework.recipe.commands.RecipeCommand;
import mjzguru.com.springframework.recipe.converters.RecipeCommandToRecipe;
import mjzguru.com.springframework.recipe.converters.RecipeToRecipeCommand;
import mjzguru.com.springframework.recipe.domain.Recipe;
import mjzguru.com.springframework.recipe.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
//@SpringBootTest
//@DataJpaTest // if we use dataJPA our test will fail because it brings up a light weight spring context and can not find  RecipeService implementation
@SpringBootTest // by using this, we can bring up the whole context based on spring boot configuration
public class RecipeServiceIT {

    // I think when we want to do an integration test we can no longer use Mocks, and we need to fetch actual repositories and
    // objects inorder to test the integration between them, and since in integration tests we bring up the Spring Context so we use @Autowired
    public static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    // because we are working with Entities outside of spring, outside of transactional context
    // , so we need to set this method as Transactional to keep these entities in the context
    @Transactional
    @Test
    public void testSaveOfDescription() throws Exception {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}