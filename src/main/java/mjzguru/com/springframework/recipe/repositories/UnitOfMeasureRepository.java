package mjzguru.com.springframework.recipe.repositories;

import mjzguru.com.springframework.recipe.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {

    //dynamic finders (Query methods in Spring Data JPA) (DESCRIBED in DETAILS IN CategoryRepository)
    Optional<UnitOfMeasure> findByDescription(String description);
}
