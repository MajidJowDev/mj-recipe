package mjzguru.com.springframework.recipe.services;

import mjzguru.com.springframework.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);
}
