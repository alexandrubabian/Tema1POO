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
import java.util.*;

public class QueryFilter {

    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    private final ArrayList<Actor> filteredActors;

    private final ArrayList<String> nameFilteredActors;
    public QueryFilter(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        this.actiune = actiune;
        this.parsingInput = parsingInput;
        this.fileWriter = fileWriter;//asta l-am adaugat pentru a putea folosi apelul de writeFile
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
        if(this.actiune.getSortType().equals("asc")) {
            Collections.sort(this.filteredActors, Actor.AscName);
        } else {
            Collections.sort(this.filteredActors, Actor.DescName);
        }
        for (Actor iterator : this.filteredActors) {
            this.nameFilteredActors.add(iterator.getName());
        }
    }

    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        this.setFilteredActors();
        message = "Query result: " + this.nameFilteredActors.toString();
        return this.fileWriter.writeFile(this.actiune.getActionId(), null, message);
    }
}