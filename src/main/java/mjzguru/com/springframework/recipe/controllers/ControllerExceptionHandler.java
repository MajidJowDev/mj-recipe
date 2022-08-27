package mjzguru.com.springframework.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

@Slf4j
@ControllerAdvice //By default, the methods in an @ControllerAdvice apply globally to all controllers
// for example the Number Format Exception can happen in all controllers so we move that method to this Class
// so that it can be used globally between all controllers
public class ControllerExceptionHandler {


    // we moved this method here so that all controllers can use it globally
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //@ExceptionHandler(NumberFormatException.class)  // use this in spring MVC
    @ExceptionHandler(WebExchangeBindException.class) // use this in webflux
    public String handleNumberFormat(Exception exception, Model model) { // here we do not need to implement NumberFormat Exception for the Recipe
        // service the exception param will be filled with default sys message

        log.error("Handling Number Format exception!");
        log.error(exception.getMessage());

        model.addAttribute("exception", exception);

        return "400error";

        /*
        ModelAndView modelAndView = new ModelAndView(); // modelAndView is used specifically for servlet API and does not work in Reactive environment

        modelAndView.setViewName("400error"); // for bad request we should use status code 400
        modelAndView.addObject("exception", exception); // added to display the exception info on the ErrorPage

        return modelAndView;
        */
    }

}
