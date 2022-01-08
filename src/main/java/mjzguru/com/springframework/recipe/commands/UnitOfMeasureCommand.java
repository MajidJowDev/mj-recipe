package mjzguru.com.springframework.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Command objects are used to collect all information on a form
// we use these command objects to avoid exposing real domain objects to and from web tier
@Getter
@Setter
@NoArgsConstructor
public class UnitOfMeasureCommand {
    private Long id;
    private String description;
}