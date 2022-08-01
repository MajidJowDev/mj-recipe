package mjzguru.com.springframework.recipe.repositories;

import mjzguru.com.springframework.recipe.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, String> {


    //dynamic finders (Query methods in Spring Data JPA)
    // we can use spring Data JPA to find Objects by their properties by using Optional<ObjectName> findBy{propertyName}
    // with this approach we just create the method name inside the interface and then Spring Data JPA provides the implementation
    // (Meaning we don't need to implement the method body)
    // and also Hibernate does the required works to fetch some data (that we need) out of DB
    // all this work will be done by Spring Data JPA without writing SQL
    Optional<Category>  findByDescription(String description);
}
