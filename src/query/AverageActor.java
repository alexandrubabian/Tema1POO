package query;

import fileio.ActionInputData;
import fileio.Writer;
import myclasses.Actor;
import myclasses.ParsingInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public final class AverageActor {

    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    private final ArrayList<String> bestActors;

    public AverageActor(final ActionInputData actiune, final ParsingInput parsingInput,
                        final Writer fileWriter) {
        this.actiune = actiune;
        this.parsingInput = parsingInput;
        this.fileWriter = fileWriter;
        this.bestActors = new ArrayList<>();
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
    /**
     * sorting the actors by their averageRating if it is greater than 0
     * and setting the parameter bestActors with their names, but just in a number
     * that is specified in each action
     */
    public void setBestActors() {
        ArrayList<Actor> copy = new ArrayList<>();
        for (Actor iterator : this.parsingInput.getActors()) {
            iterator.setAverageRating(this.parsingInput);
            if (iterator.getAverageRating() > 0.0) {
                copy.add(iterator);
            }
        }

        if (this.actiune.getSortType().equals("asc")) {
            Collections.sort(copy, Actor.ascAverageRating.thenComparing(Actor.ascName));
        } else {
            Collections.sort(copy, Actor.descAverageRating.thenComparing(Actor.descName));
        }
        if (copy.size() < this.actiune.getNumber()) {
            for (Actor iterator : copy) {
                this.bestActors.add(iterator.getName());
            }
        } else {
            for (int i = 0; i < this.actiune.getNumber(); i++) {
                this.bestActors.add(copy.get(i).getName());
            }
        }
    }
    /**
     * returning the JSONObject
     */
    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        this.setBestActors();
        message = "Query result: " + this.bestActors.toString();
        return this.fileWriter.writeFile(this.actiune.getActionId(), null, message);
    }
}
