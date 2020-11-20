package query;

import fileio.ActionInputData;
import fileio.ShowInput;
import fileio.Writer;
import myclasses.ParsingInput;

import java.util.ArrayList;

public class RatSerials extends RatAbstract{
    public RatSerials(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        super(actiune, parsingInput, fileWriter);
    }

    @Override
    public void abstractMethod() {
        ArrayList<ShowInput> copy=new ArrayList<>(this.getParsingInput().getSerials());
        this.setMostRated(copy);
    }
}
