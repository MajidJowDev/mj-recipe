package mjzguru.com.springframework.recipe.domain;

import lombok.*;
import org.springframework.data.annotation.Id;


//@Data
@Getter
@Setter
public class Notes {

    @Id
    private String id;
    //private Recipe recipe; // removed to avoid circular dependency in Mongo docs
    private String recipeNotes;

}
