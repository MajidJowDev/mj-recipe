package mjzguru.com.springframework.recipe.services;

import mjzguru.com.springframework.recipe.commands.RecipeCommand;
import mjzguru.com.springframework.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long id);

    //unlike above methods, for methods that return void we have to imply the return type
    void deleteById(Long idToDelete);
}
