package mjzguru.com.springframework.recipe.services;

import lombok.extern.slf4j.Slf4j;
import mjzguru.com.springframework.recipe.domain.Recipe;
import mjzguru.com.springframework.recipe.repositories.RecipeRepository;
import mjzguru.com.springframework.recipe.repositories.reactive.RecipeReactiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    public ImageServiceImpl(RecipeReactiveRepository recipeReactiveRepository) {
        this.recipeReactiveRepository = recipeReactiveRepository;
    }

    @Override
    @Transactional
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {
        log.debug("Received a file");

        /*
        try {
            // getting the recipe we want to save image for
            Recipe recipe = recipeRepository.findById(recipeId).get();

            // getting the bytes of received file
            Byte[] byteObject = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                byteObject[i++] = b;
            }

            recipe.setImage(byteObject);

            recipeReactiveRepository.save(recipe);

        } catch (IOException e){

            log.error("Error occured", e);

            e.printStackTrace();
        }

         */

        Mono<Recipe> recipeMono = recipeReactiveRepository.findById(recipeId)
                .map(recipe -> {
                    Byte[] byteObjects = new Byte[0];
                    try {
                        byteObjects = new Byte[file.getBytes().length]; // getting the byte space needed for saving the received file

                        int i = 0;

                        for (byte b : file.getBytes()) {
                            byteObjects[i++] = b;
                        }

                        recipe.setImage(byteObjects);

                        return recipe;

                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });

        recipeReactiveRepository.save(recipeMono.block()).block();

        return Mono.empty();

    }
}
