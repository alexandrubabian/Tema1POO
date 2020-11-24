package query;

import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.ShowInput;
import fileio.Writer;
import myclasses.ParsingInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public final class MostViewedSer {

    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    private final ArrayList<String> mostViews;

    public MostViewedSer(final ActionInputData actiune, final ParsingInput parsingInput,
                         final Writer fileWriter) {
        this.actiune = actiune;
        this.parsingInput = parsingInput;
        this.fileWriter = fileWriter;
        this.mostViews = new ArrayList<>();
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
    /**
     * sorting the serials by the number of views, I
     * check for their year and genre if they are the same as the filers, by a big hardcode and
     * adding in my arraylist mostViews their title in a number that is specified in each action or
     * the entire list of shows if the size is lower than the number
     */
    public void setMostViewed() {
        ArrayList<SerialInputData> copy = new ArrayList<>(this.parsingInput.getSerials());
        if (this.actiune.getSortType().equals("asc")) {
            Collections.sort(copy, ShowInput.ascViews.thenComparing(ShowInput.ascName));
        } else {
            Collections.sort(copy, ShowInput.descViews.thenComparing(ShowInput.descName));
        }
        int i = 0, j = 0;
        while (i < this.actiune.getNumber() && j < copy.size()) {
            if (copy.get(j).getNoOfViews() != 0) {
                if (this.actiune.getFilters().get(1).get(0) != null) {
                    if (copy.get(j).getGenres().containsAll(this.actiune.getFilters().get(1))) {
                        if (this.actiune.getFilters().get(0).get(0) == null) {
                            this.mostViews.add(copy.get(j).getTitle());
                            i++;
                        } else {
                            if (copy.get(j).getYear()
                                    == Integer.parseInt(actiune.getFilters().get(0).get(0))) {
                                this.mostViews.add(copy.get(j).getTitle());
                                i++;
                            }
                        }
                    }
                } else {
                    if (this.actiune.getFilters().get(0).get(0) == null) {
                        this.mostViews.add(copy.get(j).getTitle());
                        i++;
                    } else {
                        if (copy.get(j).getYear()
                                == Integer.parseInt(actiune.getFilters().get(0).get(0))) {
                            this.mostViews.add(copy.get(j).getTitle());
                            i++;
                        }
                    }
                }
            }
            j++;
        }

    }
    /**
     * returning the JSONObject
     */
    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        this.setMostViewed();
        message = "Query result: " + this.mostViews.toString();
        return this.fileWriter.writeFile(this.actiune.getActionId(), null, message);
    }
}
