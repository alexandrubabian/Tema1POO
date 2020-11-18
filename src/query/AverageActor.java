package query;

import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.Writer;
import myclasses.Actor;
import myclasses.Movie;
import myclasses.ParsingInput;
import myclasses.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AverageActor {

    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    private final ArrayList<String> bestActors;


    public AverageActor(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        this.actiune = actiune;
        this.parsingInput = parsingInput;
        this.fileWriter = fileWriter;//asta l-am adaugat pentru a putea folosi apelul de writeFile
        this.bestActors=new ArrayList<>();
    }

    public ArrayList<String> getBestActors() {
        return bestActors;
    }

    public ActionInputData getActiune() {
        return actiune;
    }

    public ParsingInput getInput() {
        return parsingInput;
    }

    public Writer getFileWriter() {
        return fileWriter;
    }

    public void setBestActors() {
        for (Actor iterator : this.parsingInput.getActors()) {
                iterator.setAverageRating(this.parsingInput);
        }
        ArrayList<Actor> copy = new ArrayList<>(this.parsingInput.getActors());
        if (this.actiune.getSortType().equals("asc")) {
            Collections.sort(copy, Actor.AscAverageRating.thenComparing(Actor.AscName));
        } else {
            Collections.sort(copy, Actor.DescAverageRating.thenComparing(Actor.DescName));
        }
        if (this.parsingInput.getActors().size() <this.actiune.getNumber()) {
            for (Actor iterator : copy) {
                    this.bestActors.add(iterator.getName());
            }
        } else {
            for (int i = 0; i < this.actiune.getNumber(); i++) {
                    this.bestActors.add(copy.get(i).getName());
            }
        }
    }

    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        this.setBestActors();
        message = "Query result: " + this.bestActors.toString();
        return this.fileWriter.writeFile(this.actiune.getActionId(), null, message);
    }
}