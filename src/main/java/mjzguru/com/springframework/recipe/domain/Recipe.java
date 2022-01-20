package mjzguru.com.springframework.recipe.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//@Data // this annotation is from Lombok and adds (override) the default getter and setters (and also equals and hashcode and ToString)
// to the class in background (so by adding this annotation all getters and setters will be removed from the class and will be handled by @Data)
// note: if we want to keep some of the Getters and Setters (that may have some other logics in their body) we have to add @Data without using
// Refactor menu, and remove the simple getters and setters ourselves.
@Getter
@Setter
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    // ORDINAL saves the index numbers in db But STRING saves the name in db
    @Enumerated(EnumType.STRING) // EnumType.ORDINAL did not used because of the possibilty of corruption in enum indexes (in case of adding a new value in the middle of other enum values)
    private Difficulty difficulty;

    // the default cascade action is NONE
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe") // mappedBy is the relation property on child class (Ingredients) on each object of Ingredient there will be a "recipe" property
    private Set<Ingredient> ingredients = new HashSet<>();

    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();


    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
            notes.setRecipe(this);  // added to make di-directional relation easier
        }
    }

    public Recipe addIngredient(Ingredient ingredient){
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

}
