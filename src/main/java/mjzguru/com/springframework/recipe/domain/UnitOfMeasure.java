package mjzguru.com.springframework.recipe.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Data
@Getter
@Setter
@Entity
public class UnitOfMeasure { // reference table

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private String uomDescription; // Hibernate data initializer does not accept upper case in column names so this prop should be refactored to "uom_description" or "description"
    private String description;

}
