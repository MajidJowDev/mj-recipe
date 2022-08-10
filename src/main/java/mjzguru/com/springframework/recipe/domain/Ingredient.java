package mjzguru.com.springframework.recipe.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;


import java.math.BigDecimal;
import java.util.UUID;

//@Data
@Getter
@Setter
public class Ingredient {

    //@Id
    // because ingredient is nested list property in a Mongo Doc (recipe doc) it does not get an Id vale
    // , and we do not have a reliable distinct property on this, so even if we use @Id, spring or mongo does not
    // give this object an Id value, because it is a list property
    // , so we need a way to get back to this obj so I used uuid for Id prop
    private String id = UUID.randomUUID().toString();
    private String description;
    private BigDecimal amount;

    //@DBRef //since using @DBRef is not recommended even by the MongoDB guys, we should store the IDs and load the related data on app level as an alternative
    private UnitOfMeasure uom;
    //private Recipe recipe; // removed to avoid circular dependency in Mongo docs

    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    // this constructor can be removed entirely because we removed recipe from, but for avoid changing data loader, we keep it this way for now
    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) { // I think recipe arg should be removed also since we do not have that in this class
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        //this.recipe = recipe; // removed to avoid circular dependency in Mongo docs
    }

}
