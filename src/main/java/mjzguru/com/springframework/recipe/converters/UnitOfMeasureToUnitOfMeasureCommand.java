package mjzguru.com.springframework.recipe.converters;

import lombok.Synchronized;
import mjzguru.com.springframework.recipe.commands.UnitOfMeasureCommand;
import mjzguru.com.springframework.recipe.domain.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    // since spring does not guarantee thread safety, so we use Project Lombok Synchronized and thread-safe so we can run this in a multi-threaded environment
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {

        if (unitOfMeasure != null) {
            final UnitOfMeasureCommand uomc = new UnitOfMeasureCommand(); // declaring variables final so they will be immutable, so this will give us a little more code security
            uomc.setId(unitOfMeasure.getId());
            uomc.setDescription(unitOfMeasure.getDescription());
            return uomc;
        }
        return null;
    }
}
