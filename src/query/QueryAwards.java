package query;

import actor.ActorsAwards;
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

public class QueryAwards {

    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    private final ArrayList<Actor> awardActors;

    private final ArrayList<String> nameOfAwardActors;

    public QueryAwards(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        this.actiune = actiune;
        this.parsingInput = parsingInput;
        this.fileWriter = fileWriter;//asta l-am adaugat pentru a putea folosi apelul de writeFile
        this.awardActors = new ArrayList<>();
        this.nameOfAwardActors = new ArrayList<>();
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

    public ArrayList<Actor> getAwardActors() {
        return awardActors;
    }

    public ArrayList<String> getNameOfAwardActors() {
        return nameOfAwardActors;
    }

    public void setAwardActors() {
        //trebuie sa vad daca toate alea din this.actiune.awards apartin unui actor
        ArrayList<ActorsAwards> array;
        for (Actor actorIterator : this.parsingInput.getActors()) {
            array= new ArrayList<ActorsAwards>(actorIterator.getAwards().keySet());
            if (array.containsAll(this.actiune.getFilters().get(3))) {
                this.awardActors.add(actorIterator);
            }
        }
        if (this.actiune.getSortType().equals("asc")) {
            Collections.sort(this.awardActors, Actor.AscNoAwards);
        } else {
            Collections.sort(this.awardActors, Actor.DescNoAwards);
        }
        for (Actor actorIterator : this.awardActors) {
            this.nameOfAwardActors.add(actorIterator.getName());
        }
    }

    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        this.setAwardActors();
        message = "Query result: " + this.nameOfAwardActors.toString();
        return this.fileWriter.writeFile(this.actiune.getActionId(), null, message);
    }
}
