package mjzguru.com.springframework.recipe.domain;

import lombok.*;

import javax.persistence.*;

//@Data
@Getter
@Setter
public class Notes {

    private String id;
    private Recipe recipe;
    private String recipeNotes;

}
