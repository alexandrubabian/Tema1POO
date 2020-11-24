package query;

import fileio.ActionInputData;
import fileio.Writer;
import myclasses.Actor;
import myclasses.ParsingInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class QueryFilter {

    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    private final ArrayList<Actor> filteredActors;

    private final ArrayList<String> nameFilteredActors;

    public QueryFilter(final ActionInputData actiune, final ParsingInput parsingInput,
                       final Writer fileWriter) {
        this.actiune = actiune;
        this.parsingInput = parsingInput;
        this.fileWriter = fileWriter;
        this.filteredActors = new ArrayList<>();
        this.nameFilteredActors = new ArrayList<>();
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
     * Transforming the parameter description in an array of words, i check for this array
     * if it has all key words that are specified in each action and then sorting them
     * alphabetically
     */
    public void setFilteredActors() {
        String[] words;
        List<String> array;
        ArrayList<String> loweredArray;
        for (Actor iterator : this.parsingInput.getActors()) {
            loweredArray = new ArrayList<>();
            words = iterator.getCareerDescription().split("\\W+");
            array = Arrays.asList(words);
            for (String stringIterator : array) {
                loweredArray.add(stringIterator.toLowerCase());
            }
            if (loweredArray.containsAll(this.actiune.getFilters().get((2)))) {
                this.filteredActors.add(iterator);
            }
        }
        if (this.actiune.getSortType().equals("asc")) {
            Collections.sort(this.filteredActors, Actor.ascName);
        } else {
            Collections.sort(this.filteredActors, Actor.descName);
        }
        for (Actor iterator : this.filteredActors) {
            this.nameFilteredActors.add(iterator.getName());
        }
    }
    /**
     * returning the JSONObject
     */
    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        this.setFilteredActors();
        message = "Query result: " + this.nameFilteredActors.toString();
        return this.fileWriter.writeFile(this.actiune.getActionId(), null, message);
    }
}
