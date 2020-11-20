package query;

import fileio.ActionInputData;
import fileio.ShowInput;
import fileio.Writer;
import myclasses.ParsingInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public abstract class FavAbstract {
    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    private final ArrayList<String> mostFav;

    public FavAbstract(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        this.actiune = actiune;
        this.parsingInput = parsingInput;
        this.fileWriter = fileWriter;
        this.mostFav = new ArrayList<>();
    }

    public ActionInputData getActiune() {
        return actiune;
    }

    public ParsingInput getParsingInput() {
        return parsingInput;
    }

    public Writer getFileWriter() {
        return fileWriter;
    }

    public ArrayList<String> getMostFav() {
        return mostFav;
    }

    public abstract void abstractMethod();

    public void setMostFav(ArrayList<ShowInput> copy) {
        Collections.sort(copy, ShowInput.DescFavorites);
        int i=0, j=0;
        while (i < this.actiune.getNumber() && j < copy.size()) {
            if (copy.get(j).getNoOfFavorites() != 0) {
                if (this.actiune.getFilters().get(1) != null) {
                    if (copy.get(j).getGenres().containsAll(this.actiune.getFilters().get(1))) {
                        if (this.actiune.getFilters().get(0).get(0) == null) {
                            this.mostFav.add(copy.get(j).getTitle());
                            i++;
                        } else {
                            if (copy.get(j).getYear() == Integer.parseInt(actiune.getFilters().get(0).get(0))) {
                                this.mostFav.add(copy.get(j).getTitle());
                                i++;
                            }
                        }
                    }
                } else {
                    if (this.actiune.getFilters().get(0).get(0) == null) {
                        this.mostFav.add(copy.get(j).getTitle());
                        i++;
                    } else {
                        if (copy.get(j).getYear() == Integer.parseInt(actiune.getFilters().get(0).get(0))) {
                            this.mostFav.add(copy.get(j).getTitle());
                            i++;
                        }
                    }
                }
            }
            j++;
        }
    }

    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        this.abstractMethod();
        message = "Query result: " + this.mostFav.toString();
        return this.fileWriter.writeFile(this.actiune.getActionId(), null, message);
    }
}
