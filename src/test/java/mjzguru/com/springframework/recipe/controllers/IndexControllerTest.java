package mjzguru.com.springframework.recipe.controllers;

import mjzguru.com.springframework.recipe.domain.Recipe;
import mjzguru.com.springframework.recipe.repositories.CategoryRepository;
import mjzguru.com.springframework.recipe.repositories.RecipeRepository;
import mjzguru.com.springframework.recipe.repositories.UnitOfMeasureRepository;
import mjzguru.com.springframework.recipe.repositories.reactive.CategoryReactiveRepository;
import mjzguru.com.springframework.recipe.repositories.reactive.RecipeReactiveRepository;
import mjzguru.com.springframework.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import mjzguru.com.springframework.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.mock.http.server.reactive.*;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Ignore
public class IndexControllerTest {

    IndexController indexController;

    @Mock
    RecipeService recipeService;
    @Mock
    CategoryReactiveRepository categoryReactiveRepository;
    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(categoryReactiveRepository, unitOfMeasureReactiveRepository, recipeReactiveRepository, recipeService );

    }

    @Test
    public void testMockMVC() throws Exception{
        // testing mvc controllers and Unit Test them, brings a mock servlet context (mock dispatcher servlet)
        //using it in a standalone setup because if we bring in the web context (spring context) our test is
        // no longer a unit test and will become an integration test
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        when(recipeService.getRecipes()).thenReturn(Flux.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

    }

    @Test
    public void getIndexPage() throws Exception{

        //given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        //recipes.add(new Recipe()); // removed because java take it as an equal object as the object in above line and merge them as one Object
        Recipe recipe = new Recipe();
        recipe.setId("1");
        recipes.add(recipe);

        //when(recipeService.getRecipes()).thenReturn(recipes);
        when(recipeService.getRecipes()).thenReturn(Flux.fromIterable(recipes));

        //ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);


        //When
        String viewNameReturnValue = "index"; // expected value

        //then
        assertEquals(viewNameReturnValue,indexController.getIndexPage(model)); // first param = expected, second param = actual

        verify(recipeService, times(1)).getRecipes();
        //verify(model, times(1)).addAttribute("recipes", recipeService.getRecipes());
        // also checks if passed params to addAttribute method are like the following (first param is "recipe" amd second param is some Set or List
        //verify(model, times(1)).addAttribute(eq("recipes"), anySet());
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        //Set<Recipe> setInController = argumentCaptor.getValue();
        List<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());


    }
}