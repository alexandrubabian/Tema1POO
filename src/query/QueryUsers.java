package query;

import fileio.ActionInputData;
import fileio.Writer;
import myclasses.ParsingInput;
import myclasses.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public final class QueryUsers {

    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    private final ArrayList<String> mostActiveUsers;

    public QueryUsers(final ActionInputData actiune, final ParsingInput parsingInput,
                      final Writer fileWriter) {
        this.actiune = actiune;
        this.parsingInput = parsingInput;
        this.fileWriter = fileWriter;
        this.mostActiveUsers = new ArrayList<>();
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

    public ArrayList<String> getMostActiveUsers() {
        return mostActiveUsers;
    }
    /**
     * This function set the mostActiveUsers by the sort type specified in each action
     */
    public void setSortedUsers() {
        ArrayList<User> copy = new ArrayList<>(this.parsingInput.getUsers());
        if (this.actiune.getSortType().equals("asc")) {
            Collections.sort(copy, User.ascRating.thenComparing(User.ascName));
        } else {
            Collections.sort(copy, User.descRating.thenComparing(User.descName));
        }
        if (this.parsingInput.getUsers().size() < this.actiune.getNumber()) {
            for (User iterator : copy) {
                if (iterator.getNumberOfRatings() != 0) {
                    this.mostActiveUsers.add(iterator.getUsername());
                }
            }
        } else {
            int i = 0, j = 0;
            while (i < this.actiune.getNumber() && j < copy.size()) {
                if (copy.get(j).getNumberOfRatings() != 0) {
                    this.mostActiveUsers.add(copy.get(j).getUsername());
                    i++;
                }
                j++;
            }
        }
    }
    /**
     * returning the JSONObject
     */
    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        this.setSortedUsers();
        message = "Query result: " + this.mostActiveUsers.toString();
        return this.fileWriter.writeFile(this.actiune.getActionId(), null, message);
    }
}
