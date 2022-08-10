package mjzguru.com.springframework.recipe.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

//@Data // this annotation is from Lombok and adds (override) the default getter and setters (and also equals and hashcode and ToString)
      // to the class in background (so by adding this annotation all getters and setters will be removed from the class and will be handled by @Data)
      // note: if we want to keep some of the Getters and Setters (that may have some other logics in their body) we have to add @Data without using
      // Refactor menu, and remove the simple getters and setters ourselves.
@Getter
@Setter
@Document // explicitly tell Spring that this model is a mongo Document
public class Category {

    @Id
    private String id;
    private String description;

    //@DBRef // we use this annotation to the reference document related to this document (setting up bidirectional mapping)
    //since using @DBRef is not recommended even by the MongoDB guys, we should store the IDs and load the related data on app level as an alternative
    private Set<Recipe> recipes;

}
