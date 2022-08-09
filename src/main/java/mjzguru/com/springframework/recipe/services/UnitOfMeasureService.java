package mjzguru.com.springframework.recipe.services;

import mjzguru.com.springframework.recipe.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface UnitOfMeasureService {

    //Set<UnitOfMeasureCommand> listAllUoms();
    Flux<UnitOfMeasureCommand> listAllUoms(); // Flux: Reactive type that represents Many ()
}
