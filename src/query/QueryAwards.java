package query;

import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.Writer;
import myclasses.Actor;
import myclasses.Movie;
import myclasses.ParsingInput;
import myclasses.User;
import utils.Utils;

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
        ArrayList<ActorsAwards> filtersActiune = new ArrayList<>();
        for (String iterator : this.actiune.getFilters().get(3)) {
            filtersActiune.add(Utils.stringToAwards(iterator));
        }
        for (Actor actorIterator : this.parsingInput.getActors()) {
            for(int number : actorIterator.getAwards().values()) {
                actorIterator.incrementNoOfAwards(number);
            }
            array= new ArrayList<ActorsAwards>(actorIterator.getAwards().keySet());


            if (array.containsAll(filtersActiune)) {
                this.awardActors.add(actorIterator);
            }
        }
        if (this.actiune.getSortType().equals("asc")) {
            Collections.sort(this.awardActors, Actor.AscNoAwards.thenComparing(Actor.AscName));
        } else {
            Collections.sort(this.awardActors, Actor.DescNoAwards.thenComparing(Actor.DescName));
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
