package mjzguru.com.springframework.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice //By default, the methods in an @ControllerAdvice apply globally to all controllers
// for example the Number Format Exception can happen in all controllers so we move that method to this Class
// so that it can be used globally between all controllers
public class ControllerExceptionHandler {

    /*
    // we moved this method here so that all controllers can use it globally
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception exception) { // here we do not need to implement NumberFormat Exception for the Recipe
        // service the exception param will be filled with default sys message

        log.error("Handling Number Format exception!");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("400error"); // for bad request we should use status code 400
        modelAndView.addObject("exception", exception); // added to display the exception info on the ErrorPage

        return modelAndView;
    }
    */
}
