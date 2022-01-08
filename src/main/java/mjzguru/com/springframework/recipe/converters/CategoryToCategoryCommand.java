package mjzguru.com.springframework.recipe.converters;

import lombok.Synchronized;
import mjzguru.com.springframework.recipe.commands.CategoryCommand;
import mjzguru.com.springframework.recipe.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized // since spring does not guarantee thread safety, so we use Project Lombok Synchronized and thread-safe so we can run this in a multi-threaded environment
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }

        final CategoryCommand categoryCommand = new CategoryCommand(); // declaring variables final so they will be immutable, so this will give us a little more code security

        categoryCommand.setId(source.getId());
        categoryCommand.setDescription(source.getDescription());

        return categoryCommand;
    }
}