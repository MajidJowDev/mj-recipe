package mjzguru.com.springframework.recipe.services;

import lombok.extern.slf4j.Slf4j;
import mjzguru.com.springframework.recipe.commands.RecipeCommand;
import mjzguru.com.springframework.recipe.converters.RecipeCommandToRecipe;
import mjzguru.com.springframework.recipe.converters.RecipeToRecipeCommand;
import mjzguru.com.springframework.recipe.domain.Recipe;
import mjzguru.com.springframework.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j // logger provided by Lombok
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");

        Set<Recipe> recipeSet = new HashSet<>();

        // adds all objects of RecipeRepository to recipeSet
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);

        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if(!recipeOptional.isPresent()){
            throw new RuntimeException("Recipe Not Found!");
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        // recipe command comes in and will be converted to a Recipe POJO, it's not a Hibernate object so we call it detachedRecipe
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        // spring DataJPA is going to create a new entity if the object is new and if the object exists, it's going to do a merge operation
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    // because we are working with Entities outside of spring, outside of transactional context
    // , so we need to set this method as Transactional to keep these entities in the context
    // we are doing a conversion outside the scope (the commands are not Hibernate objects nor Spring Beans)
    // , so if we hit any lazily loaded properties our method will crash, so we expand the transactional scope to this method
    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    public void deleteById(Long idToDelete) {
        recipeRepository.deleteById(idToDelete);
    }
}
