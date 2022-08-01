package mjzguru.com.springframework.recipe.repositories;

import mjzguru.com.springframework.recipe.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

/*
Note: we don't need Repositories for Ingredient, Notes and Difficulty because we won't work with them
    independently and we set their relation in Recipe POJO with cascadeType.ALL, so any CRUD operation
     on Recipe will be done on "Ingredient" and "Notes" also
 */
public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
