package mjzguru.com.springframework.recipe.converters;

import lombok.Synchronized;
import mjzguru.com.springframework.recipe.commands.NotesCommand;
import mjzguru.com.springframework.recipe.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    // since spring does not guarantee thread safety, so we use Project Lombok Synchronized and thread-safe so we can run this in a multi-threaded environment
    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if (source == null) {
            return null;
        }

        final NotesCommand notesCommand = new NotesCommand(); // declaring variables final so they will be immutable, so this will give us a little more code security
        notesCommand.setId(source.getId());
        notesCommand.setRecipeNotes(source.getRecipeNotes());
        return notesCommand;
    }
}