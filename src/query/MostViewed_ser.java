package query;

import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.ShowInput;
import fileio.Writer;
import myclasses.Actor;
import myclasses.Movie;
import myclasses.ParsingInput;
import myclasses.User;

import java.io.IOException;
import java.util.*;

public class MostViewed_ser {

    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    private final ArrayList<String> mostViews;

    public MostViewed_ser(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        this.actiune = actiune;
        this.parsingInput = parsingInput;
        this.fileWriter = fileWriter;//asta l-am adaugat pentru a putea folosi apelul de writeFile
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

    public void setMostViewed() {
        ArrayList<SerialInputData> copy = new ArrayList<>(this.parsingInput.getSerials());
        if(this.actiune.getSortType().equals("asc")) {
            Collections.sort(copy,ShowInput.AscViews.thenComparing(ShowInput.AscName));
        } else {
            Collections.sort(copy,ShowInput.DescViews.thenComparing(ShowInput.DescName));
        }
        int i=0, j=0;
        while (i < this.actiune.getNumber() && j < copy.size()) {
            if (copy.get(j).getNoOfViews() != 0) {
                if (this.actiune.getFilters().get(1).get(0) != null) {
                    if (copy.get(j).getGenres().containsAll(this.actiune.getFilters().get(1))) {
                        if (this.actiune.getFilters().get(0).get(0) == null) {
                            this.mostViews.add(copy.get(j).getTitle());
                            i++;
                        } else {
                            if (copy.get(j).getYear() == Integer.parseInt(actiune.getFilters().get(0).get(0))) {
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
                        if (copy.get(j).getYear() == Integer.parseInt(actiune.getFilters().get(0).get(0))) {
                            this.mostViews.add(copy.get(j).getTitle());
                            i++;
                        }
                    }
                }
            }
            j++;
        }
//            for (int i = 0; i < this.actiune.getNumber(); i++) {
//                if (copy.get(i).getNoOfViews() != 0) {
//                    //!!!!!!!!!!!!!!!!!!!!!!!!
//                    //mai e si cazul unde nu o sa intre in acest if si nu o sa mai adauge in numarul corect
//                    //vezi unde ai mai scris prostia asta
//                    //de asemenea mai de facut sa verifici in functie de an si gen
//                    this.mostViews.add(copy.get(i).getTitle());
//                }
//            }
    }

    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        this.setMostViewed();
        message = "Query result: " + this.mostViews.toString();
        return this.fileWriter.writeFile(this.actiune.getActionId(), null, message);
    }
}