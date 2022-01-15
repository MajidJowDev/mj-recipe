package mjzguru.com.springframework.recipe.services;

import mjzguru.com.springframework.recipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
