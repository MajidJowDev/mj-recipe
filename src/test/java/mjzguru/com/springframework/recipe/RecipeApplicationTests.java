package mjzguru.com.springframework.recipe;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

//@Ignore
@RunWith(SpringRunner.class) // for JUnit 4 // brings Spring Context in Test
//@SpringBootTest // this one worked for previous dbs
//@DataJpaTest
@DataMongoTest // if we just add this and do not change our DataLoader and the way of our database initialization
// , using this annotation will not work and encounter error
class RecipeApplicationTests {

    @Test
    void contextLoads() {
    }


}
