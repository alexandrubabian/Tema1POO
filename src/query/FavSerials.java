package query;

import fileio.ActionInputData;
import fileio.ShowInput;
import fileio.Writer;
import myclasses.ParsingInput;

import java.util.ArrayList;

public final class FavSerials extends FavAbstract {

    public FavSerials(final ActionInputData actiune, final ParsingInput parsingInput,
                      final Writer fileWriter) {
        super(actiune, parsingInput, fileWriter);
    }

    @Override
    public void abstractMethod() {
        ArrayList<ShowInput> copy = new ArrayList<>(this.getParsingInput().getSerials());
        this.setMostFav(copy);
    }
}
