package mjzguru.com.springframework.recipe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // if we do not use this annotation the "getRecipeNotFoundTest()" test in "RecipeControllerTest"  will crash
// this way we can change the http status code that's coming back to the browser
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
