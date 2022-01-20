package mjzguru.com.springframework.recipe.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

//@Data
@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"}) // added to avoid getting into endless loop because of bi-drectional references and relations (lombok)
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal amount;

    @OneToOne(fetch = FetchType.EAGER) // the default fetch type is Eager but I explicitly define the rel as Eager for learning purpose
    private UnitOfMeasure uom;

    @ManyToOne
    private Recipe recipe;


    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        this.recipe = recipe;
    }

}
