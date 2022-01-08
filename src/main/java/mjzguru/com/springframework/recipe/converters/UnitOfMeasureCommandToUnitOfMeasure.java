package mjzguru.com.springframework.recipe.converters;

import lombok.Synchronized;
import mjzguru.com.springframework.recipe.commands.UnitOfMeasureCommand;
import mjzguru.com.springframework.recipe.domain.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;



@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    // since spring does not guarantee thread safety, so we use Project Lombok Synchronized and thread-safe so we can run this in a multi-threaded environment
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if(source == null){
            return null;
        }
        final UnitOfMeasure uom = new UnitOfMeasure(); // declaring variables final so they will be immutable, so this will give us a little more code security
        uom.setId(source.getId());
        uom.setDescription(source.getDescription());
        return uom;
    }
}
