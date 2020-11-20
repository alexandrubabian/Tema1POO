package query;

import fileio.ActionInputData;
import fileio.ShowInput;
import fileio.Writer;
import myclasses.ParsingInput;

import java.util.ArrayList;

public class FavMovies extends FavAbstract{

    public FavMovies(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        super(actiune, parsingInput, fileWriter);
    }

    @Override
    public void abstractMethod() {
        ArrayList<ShowInput> copy=new ArrayList<>(this.getParsingInput().getMovies());
        this.setMostFav(copy);
    }
}
