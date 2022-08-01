package mjzguru.com.springframework.recipe.domain;

import org.junit.Before;

import static org.junit.Assert.*;

public class CategoryTest {

    Category category;

    @Before // added before testing methods in current unit test class, (to be used for testing some of or all methods)
    public void setUp(){
        category = new Category();
    }

    @org.junit.Test
    public void getId() {
        String idValue = "4";

        category.setId(idValue);

        assertEquals(idValue, category.getId());
    }

    @org.junit.Test
    public void getDescription() {
    }

    @org.junit.Test
    public void getRecipes() {
    }
}