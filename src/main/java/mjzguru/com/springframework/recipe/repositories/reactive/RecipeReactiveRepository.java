package mjzguru.com.springframework.recipe.repositories.reactive;

import mjzguru.com.springframework.recipe.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
