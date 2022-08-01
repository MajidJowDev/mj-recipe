package mjzguru.com.springframework.recipe.services;

import lombok.extern.slf4j.Slf4j;
import mjzguru.com.springframework.recipe.domain.Recipe;
import mjzguru.com.springframework.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;
    public ImageServiceImpl(RecipeRepository recipeService) {
        this.recipeRepository = recipeService;
    }

    @Override
    @Transactional
    public void saveImageFile(String recipeId, MultipartFile file) {
        log.debug("Received a file");

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

            recipeRepository.save(recipe);

        } catch (IOException e){

            log.error("Error occured", e);

            e.printStackTrace();
        }

    }
}
