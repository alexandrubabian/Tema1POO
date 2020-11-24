package recommendation;

import fileio.ActionInputData;
import fileio.Writer;
import myclasses.ParsingInput;

public abstract class RecomAbstract {

    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    public RecomAbstract(final ActionInputData actiune, final ParsingInput parsingInput,
                         final Writer fileWriter) {
        this.actiune = actiune;
        this.parsingInput = parsingInput;
        this.fileWriter = fileWriter;
    }

    public final ActionInputData getActiune() {
        return actiune;
    }

    public final ParsingInput getParsingInput() {
        return parsingInput;
    }

    public final Writer getFileWriter() {
        return fileWriter;
    }
}
