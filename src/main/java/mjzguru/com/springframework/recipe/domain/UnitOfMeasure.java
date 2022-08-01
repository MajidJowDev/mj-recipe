package mjzguru.com.springframework.recipe.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Data
@Getter
@Setter
public class UnitOfMeasure { // reference table

    private String id;
    private String description;

}
