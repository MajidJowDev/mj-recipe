
package mjzguru.com.springframework.recipe.repositories;

import mjzguru.com.springframework.recipe.bootstrap.DataLoader;
import mjzguru.com.springframework.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

//@Ignore
@RunWith(SpringRunner.class)
//@DataJpaTest
@DataMongoTest
public class UnitOfMeasureRepositoryTest {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Before
    // mimicking what spring do for us in the spring context
    public void setUp() throws Exception {
        // since we have two test methods in this class, so th @Before will be executed twice and causes duplicate records for
        //categories, and UOMs, so we need to delete them in the beginning to avoid duplicates
        //, so basically we do this for resetting the database
        //mongo db does not have concept of transaction in JPA world, but default behaviour of Spring is to roll back
        // after each test method, so integration test will stay cleaner, and since mongo does not have transaction
        // the best way to handle it is going by cleaning the database manually and then call our bootstrap(dataloader)
        // and initialize the data this way
        // other way could be setting a special test configuration to bring in the bootstrap(DataLoader) package and ask Spring to wire up that bean
        recipeRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();
        categoryRepository.deleteAll();

        DataLoader recipeBootstrap = new DataLoader( recipeRepository, unitOfMeasureRepository, categoryRepository);

        recipeBootstrap.onApplicationEvent(null); // because we only use this on application event and we do not use it in the context
    }

    @Test
    public void findByDescription() {
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        assertEquals("Teaspoon", uomOptional.get().getDescription());
    }

    @Test
    //@DirtiesContext // add to make Spring Context one moretime for this method
    public void findByDescriptionCup() {
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Cup");
        assertEquals("Cup", uomOptional.get().getDescription());
    }
}
