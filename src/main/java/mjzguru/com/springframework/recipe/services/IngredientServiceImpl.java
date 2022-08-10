package mjzguru.com.springframework.recipe.services;

import lombok.extern.slf4j.Slf4j;
import mjzguru.com.springframework.recipe.commands.IngredientCommand;
import mjzguru.com.springframework.recipe.converters.IngredientCommandToIngredient;
import mjzguru.com.springframework.recipe.converters.IngredientToIngredientCommand;
import mjzguru.com.springframework.recipe.domain.Ingredient;
import mjzguru.com.springframework.recipe.domain.Recipe;
import mjzguru.com.springframework.recipe.repositories.RecipeRepository;
import mjzguru.com.springframework.recipe.repositories.UnitOfMeasureRepository;
import mjzguru.com.springframework.recipe.repositories.reactive.RecipeReactiveRepository;
import mjzguru.com.springframework.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    // since Reactive driver for mongoDB does not support @DBRef, if we only use reactive repositories, the lists that are defind as @DBRef s
    // will be null (a list of null values) so in this case we have to use both Reactive and non-Reactive Repositories as a work around
    private final RecipeReactiveRepository recipeReactiveRepository;
    //private final RecipeRepository recipeRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeReactiveRepository recipeReactiveRepository,
                                 UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        //this.recipeRepository = recipeRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.recipeReactiveRepository = recipeReactiveRepository;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

        /*
        return recipeReactiveRepository.findById(recipeId)
                .map(recipe -> recipe.getIngredients()
                        .stream()
                        .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                        .findFirst())
                .map(ingredient -> {
                    IngredientCommand command = ingredientToIngredientCommand.convert(ingredient.get());
                    command.setRecipeId(recipeId);
                    return command;
                });
         */
        // above implementation is correct also but a better way would also be the following implementation
        return recipeReactiveRepository
                .findById(recipeId)
                .flatMapIterable(Recipe::getIngredients) // in this implementation we use flatMapIterable instead of stream ... (Although Stream may have better performance based on my previous searches)
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                .single()
                .map(ingredient -> {
                    IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
                    command.setRecipeId(recipeId);
                    return command;
                });

        // non-reactive
        /*
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()){
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){

            log.error("Ingredient id not found: " + ingredientId);
        }

        //enhance command object with recipe id
        IngredientCommand ingredientCommand = ingredientCommandOptional.get();
        ingredientCommand.setRecipeId(recipe.getId());

        return  Mono.just(ingredientCommandOptional.get());
        */
    }

    @Override
    @Transactional  // to work with detached entity it's better to use transactional method
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
        // command (ingredient) is our detached entity, so first we have to get the Recipe related to the command(ingredient)
        // and then get the ingredient from it based on the command (ingredient command) id and then get the related UOM of it
        //
        //Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
        Recipe recipe = recipeReactiveRepository.findById(command.getRecipeId()).block();

        if(recipe == null){

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return  Mono.just(new IngredientCommand());
        } else {
            //Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            // if there is an ingredient with the given id (an ingredient with an id were there from before, update it)
            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureReactiveRepository
                        .findById(command.getUom().getId()).block());
                        //.orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this

                if (ingredientFound.getUom() == null){
                    new RuntimeException("UOM NOT FOUND");
                }

            } else {
                //add new Ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));

                /*
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
                 */
            }

            Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            //todo check for fail

            //enhance with id value
            IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return Mono.just(ingredientCommandSaved);
        }

    }

    @Override
    public Mono<Void> deleteById(String recipeId, String idToDelete) {

        log.debug("Deleting ingredient:" + recipeId + ":" + idToDelete);

        //Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        //Recipe recipe = recipeRepository.findById(recipeId).get();
        Recipe recipe = recipeReactiveRepository.findById(recipeId).block();
        if(recipe != null) {

//        if(recipeOptional.isPresent()){
//            Recipe recipe = recipeOptional.get();
            log.debug("found Recipe");

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredients -> ingredients.getId().equals(idToDelete))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                log.debug("Ingredient found");
                /*Ingredient ingredientToDelete = ingredientOptional.get();
                // remember to use both next two lines (their logic) for deleting an object which is related to another obj...
                // if we just use the remove the object won't be deleted
                //ingredientToDelete.setRecipe(null);// this will cause hibernate to delete the ingredient from db
                */
                recipe.getIngredients().remove(ingredientOptional.get()); // this will cause hibernate to delete the ingredient from db

                recipeReactiveRepository.save(recipe).block();
            }
        } else {
            log.debug("Recipe Id not found: " + recipeId);
        }

        // for Mono<void> return type we must use .empty()
        // Void is kind of a placeholder for generic and we just return null from the statement
        return Mono.empty();
    }


}
