package mjzguru.com.springframework.recipe.services;

import mjzguru.com.springframework.recipe.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();
}
