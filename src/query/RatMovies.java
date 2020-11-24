package query;

import fileio.ActionInputData;
import fileio.ShowInput;
import fileio.Writer;
import myclasses.ParsingInput;

import java.util.ArrayList;

public final class RatMovies extends RatAbstract {

    public RatMovies(final ActionInputData actiune, final ParsingInput parsingInput,
                     final Writer fileWriter) {
        super(actiune, parsingInput, fileWriter);
    }

    @Override
    public void abstractMethod() {
        ArrayList<ShowInput> copy = new ArrayList<>(this.getParsingInput().getMovies());
        this.setMostRated(copy);
    }
}
