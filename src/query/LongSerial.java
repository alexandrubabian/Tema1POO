package query;

import fileio.ActionInputData;
import fileio.ShowInput;
import fileio.Writer;
import myclasses.ParsingInput;

import java.util.ArrayList;

public final class LongSerial extends LongAbstract {

    public LongSerial(final ActionInputData actiune, final ParsingInput parsingInput,
                      final Writer fileWriter) {
        super(actiune, parsingInput, fileWriter);
    }

    @Override
    public void abstractMethod() {
        ArrayList<ShowInput> copy = new ArrayList<>(this.getParsingInput().getSerials());
        this.setLongest(copy);
    }
}
