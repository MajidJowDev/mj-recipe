package mjzguru.com.springframework.recipe.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Data
@Getter
@Setter
@Document
public class UnitOfMeasure { // reference table

    @Id
    private String id;
    private String description;

}
