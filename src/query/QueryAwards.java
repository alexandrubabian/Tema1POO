package query;

import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.Writer;
import myclasses.Actor;
import myclasses.ParsingInput;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public final class QueryAwards {

    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    private final ArrayList<Actor> awardActors;

    private final ArrayList<String> nameOfAwardActors;

    public QueryAwards(final ActionInputData actiune, final ParsingInput parsingInput,
                       final Writer fileWriter) {
        this.actiune = actiune;
        this.parsingInput = parsingInput;
        this.fileWriter = fileWriter;
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
    /**
     * First, setting the parameter for each actor, the number of awards that he has
     * Then checking for each actor that he has all the awards specified in each action
     * Then sorting the actors with right awards by their number of awards
     */
    public void setAwardActors() {
        final int magicNumber = 3;
        ArrayList<ActorsAwards> array;
        ArrayList<ActorsAwards> filtersActiune = new ArrayList<>();
        for (String iterator : this.actiune.getFilters().get(magicNumber)) {
            filtersActiune.add(Utils.stringToAwards(iterator));
        }
        for (Actor actorIterator : this.parsingInput.getActors()) {
            for (int number : actorIterator.getAwards().values()) {
                actorIterator.incrementNoOfAwards(number);
            }
            array = new ArrayList<ActorsAwards>(actorIterator.getAwards().keySet());


            if (array.containsAll(filtersActiune)) {
                this.awardActors.add(actorIterator);
            }
        }
        if (this.actiune.getSortType().equals("asc")) {
            Collections.sort(this.awardActors, Actor.ascNoAwards.thenComparing(Actor.ascName));
        } else {
            Collections.sort(this.awardActors, Actor.descNoAwards.thenComparing(Actor.descName));
        }
        for (Actor actorIterator : this.awardActors) {
            this.nameOfAwardActors.add(actorIterator.getName());
        }
    }
    /**
     * returning the JSONObject
     */
    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        this.setAwardActors();
        message = "Query result: " + this.nameOfAwardActors.toString();
        return this.fileWriter.writeFile(this.actiune.getActionId(), null, message);
    }
}
