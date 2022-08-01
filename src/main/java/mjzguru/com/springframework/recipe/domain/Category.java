package mjzguru.com.springframework.recipe.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

//@Data // this annotation is from Lombok and adds (override) the default getter and setters (and also equals and hashcode and ToString)
      // to the class in background (so by adding this annotation all getters and setters will be removed from the class and will be handled by @Data)
      // note: if we want to keep some of the Getters and Setters (that may have some other logics in their body) we have to add @Data without using
      // Refactor menu, and remove the simple getters and setters ourselves.
@Getter
@Setter
public class Category {

    private String id;
    private String description;
    private Set<Recipe> recipes;

}
