package mjzguru.com.springframework.recipe.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;


import java.math.BigDecimal;

//@Data
@Getter
@Setter
public class Ingredient {

    @Id
    private String id;
    private String description;
    private BigDecimal amount;

    @DBRef
    private UnitOfMeasure uom;
    //private Recipe recipe; // removed to avoid circular dependency in Mongo docs

    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) { // I think recipe arg should be removed also since we do not have that in this class
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        //this.recipe = recipe; // removed to avoid circular dependency in Mongo docs
    }

}
