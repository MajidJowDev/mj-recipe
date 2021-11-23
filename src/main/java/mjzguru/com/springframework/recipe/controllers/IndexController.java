package mjzguru.com.springframework.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"","/", "/index", "/index.html"})
    public String getIndexPage(){

        System.out.println("Some message to say...");
        return "index";
    }
}
