package mjzguru.com.springframework.recipe.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.HashSet;
import java.util.Set;

//@Data // this annotation is from Lombok and adds (override) the default getter and setters (and also equals and hashcode and ToString)
// to the class in background (so by adding this annotation all getters and setters will be removed from the class and will be handled by @Data)
// note: if we want to keep some of the Getters and Setters (that may have some other logics in their body) we have to add @Data without using
// Refactor menu, and remove the simple getters and setters ourselves.
@Getter
@Setter
@Document
public class Recipe {

    @Id
    private String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<Ingredient> ingredients = new HashSet<>();
    private Byte[] image;
    private Difficulty difficulty;
    private Notes notes;

    @DBRef // Indicates that this class is going to be used as a Reference and (this set type is also defined as a Mongo doc)
    //meaning we can only use @DBRef for the data types (classes) that are defined as a Mongo Doc
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
            //notes.setRecipe(this); // since we do not have any relation in NoSQL dbs we need to comment this part, otherwise it will cause circular dependency
        }
    }

    public Recipe addIngredient(Ingredient ingredient){
        //ingredient.setRecipe(this); // since we do not have any relation in NoSQL dbs we need to comment this part, otherwise it will cause circular dependency
        this.ingredients.add(ingredient);
        return this;
    }

}
