package query;

import fileio.ActionInputData;
import fileio.ShowInput;
import fileio.Writer;
import myclasses.ParsingInput;

import java.util.ArrayList;
import java.util.Random;

public class RatMovies extends RatAbstract {

    public RatMovies(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        super(actiune, parsingInput, fileWriter);
    }

    @Override
    public void abstractMethod() {
        ArrayList<ShowInput> copy=new ArrayList<>(this.getParsingInput().getMovies());
        this.setMostRated(copy);
    }
}
