package query;

import fileio.ActionInputData;
import fileio.ShowInput;
import fileio.Writer;
import myclasses.ParsingInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public abstract class LongAbstract {
    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    private final ArrayList<String> longest;

    public LongAbstract(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        this.actiune = actiune;
        this.parsingInput = parsingInput;
        this.fileWriter = fileWriter;
        this.longest = new ArrayList<>();
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

    public ArrayList<String> getLongest() {
        return longest;
    }

    public abstract void abstractMethod();

    public void setLongest(ArrayList<ShowInput> copy) {
        if(this.actiune.getSortType().equals("asc")) {
            Collections.sort(copy,ShowInput.AscDuration.thenComparing(ShowInput.AscName));
        } else {
            Collections.sort(copy,ShowInput.DescDuration.thenComparing(ShowInput.DescName));
        }
        int i=0, j=0;
        while (i < this.actiune.getNumber() && j < copy.size()) {

            if (copy.get(j).getDuration() != 0) {
                //this.longest.add(copy.get(j).getTitle());
                if (this.actiune.getFilters().get(1).get(0) != null) {
                    if (copy.get(j).getGenres().containsAll(this.actiune.getFilters().get(1))) {
                        if (this.actiune.getFilters().get(0).get(0) == null) {
                            this.longest.add(copy.get(j).getTitle());
                            i++;
                        } else {
                            if (copy.get(j).getYear() == Integer.parseInt(actiune.getFilters().get(0).get(0))) {
                                this.longest.add(copy.get(j).getTitle());
                                i++;
                            }
                        }
                    }
                } else {
                    if (this.actiune.getFilters().get(0).get(0) == null) {
                        this.longest.add(copy.get(j).getTitle());
                        i++;
                    } else {
                        if (copy.get(j).getYear() == Integer.parseInt(actiune.getFilters().get(0).get(0))) {
                            this.longest.add(copy.get(j).getTitle());
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
        message = "Query result: " + this.longest.toString();
        return this.fileWriter.writeFile(this.actiune.getActionId(), null, message);
    }
}
