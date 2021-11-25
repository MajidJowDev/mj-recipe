package mjzguru.com.springframework.recipe.repositories;

import mjzguru.com.springframework.recipe.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
