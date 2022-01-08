package mjzguru.com.springframework.recipe.converters;

import lombok.Synchronized;
import mjzguru.com.springframework.recipe.commands.NotesCommand;
import mjzguru.com.springframework.recipe.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    // since spring does not guarantee thread safety, so we use Project Lombok Synchronized and thread-safe so we can run this in a multi-threaded environment
    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if(source == null) {
            return null;
        }

        final Notes notes = new Notes(); // declaring variables final so they will be immutable, so this will give us a little more code security
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getRecipeNotes());
        return notes;
    }
}