package mjzguru.com.springframework.recipe.services;

import lombok.extern.slf4j.Slf4j;
import mjzguru.com.springframework.recipe.commands.RecipeCommand;
import mjzguru.com.springframework.recipe.converters.RecipeCommandToRecipe;
import mjzguru.com.springframework.recipe.converters.RecipeToRecipeCommand;
import mjzguru.com.springframework.recipe.domain.Recipe;
import mjzguru.com.springframework.recipe.exceptions.NotFoundException;
import mjzguru.com.springframework.recipe.repositories.RecipeRepository;
import mjzguru.com.springframework.recipe.repositories.reactive.RecipeReactiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j // logger provided by Lombok
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I'm in the service");

        /*
        Set<Recipe> recipeSet = new HashSet<>();

        // adds all objects of RecipeRepository to recipeSet
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);

        return recipeSet;
         */
        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        /*
        Optional<Recipe> recipeOptional = recipeReactiveRepository.findById(id);

        if(!recipeOptional.isPresent()){
            //throw new RuntimeException("Recipe Not Found!"); // if we use this exception the test will go boom
            throw new NotFoundException("Recipe Not Found for ID value: " + id.toString());
        }

        return recipeOptional.get();
        */
         return recipeReactiveRepository.findById(id);
    }

    @Override
    //@Transactional // we do not have transactional in Mongo
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        /*// recipe command comes in and will be converted to a Recipe POJO, it's not a Hibernate object so we call it detachedRecipe
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        // spring DataJPA is going to create a new entity if the object is new and if the object exists, it's going to do a merge operation
        Recipe savedRecipe = recipeReactiveRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
        */
         return recipeReactiveRepository.save(recipeCommandToRecipe.convert(command))
                 .map(recipeToRecipeCommand::convert);

    }

    // because we are working with Entities outside of spring, outside of transactional context
    // , so we need to set this method as Transactional to keep these entities in the context
    // we are doing a conversion outside the scope (the commands are not Hibernate objects nor Spring Beans)
    // , so if we hit any lazily loaded properties our method will crash, so we expand the transactional scope to this method
    @Override
    @Transactional
    public Mono<RecipeCommand> findCommandById(String id) {

        /*
        //return recipeToRecipeCommand.convert(findById(id));
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(findById(id));

        //enhance command object with id value
        if(recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0){
            recipeCommand.getIngredients().forEach(rc -> {
                rc.setRecipeId(recipeCommand.getId());
            });
        }

        return recipeCommand;
        */
        return recipeReactiveRepository.findById(id)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

                    recipeCommand.getIngredients().forEach(ic -> {
                        ic.setRecipeId(recipeCommand.getId());
                    });
                    return recipeCommand;
                });
    }

    @Override
    public Mono<Void> deleteById(String idToDelete) {
        //recipeReactiveRepository.deleteById(idToDelete);
        recipeReactiveRepository.deleteById(idToDelete).block(); // better not use block here and use it in controller

        return Mono.empty();
    }
}
