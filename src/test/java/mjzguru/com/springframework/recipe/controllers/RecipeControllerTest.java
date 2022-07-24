package mjzguru.com.springframework.recipe.controllers;

import mjzguru.com.springframework.recipe.commands.RecipeCommand;
import mjzguru.com.springframework.recipe.domain.Recipe;
import mjzguru.com.springframework.recipe.exceptions.NotFoundException;
import mjzguru.com.springframework.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
       // MockitoAnnotations.initMocks(this); // deprecated
        MockitoAnnotations.openMocks(this);

        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler()) // if we want to use Global Controller we must set the ContollerAdvice
                .build();
    }

    @Test
    public void getRecipeTest() throws Exception{

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));

    }

    @Test
    public void getRecipeNotFoundTest() throws Exception {
        //Recipe recipe = new Recipe(); // I do not think that this initiation is actually required
        //recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    public void getRecipeNumberFormatExceptionTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/asdsad1/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    public void getNewRecipeFormTest() throws Exception{
        RecipeCommand command = new RecipeCommand();

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new")) // we will get the RequestMapping path
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform")) // the view name that returned by the called method of the controller
                .andExpect(model().attributeExists("recipe")); // checks if the attribute name that we added to model in called attribute exists
    }

    @Test
    public void postNewRecipeFormTest() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command); //since in the method we save the command obj, so we use Mockito Mock to check the saved Command

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // this line mimics the form post
                .param("id", "")
                .param("description", "some test string")
                .param("directions", "some directions")
                )
                .andExpect(status().is3xxRedirection()) // checks if url redirection is done
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    public void postNewRecipeFormValidationFailTest() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("cookTime", "3000")

                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void getUpdateViewTest() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/update")) // get the RequestMapping path
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void deleteActionTest() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/")); // check if we redirected to the root

        verify(recipeService, times(1)).deleteById(anyLong());
    }
}