package commands;

import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.Writer;
import myclasses.Movie;
import myclasses.ParsingInput;
import myclasses.User;
import utils.Utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public final class View {
    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    public View(final ActionInputData actiune, final ParsingInput parsingInput,
                final Writer fileWriter) {
        this.actiune = actiune;
        this.parsingInput = parsingInput;
        this.fileWriter = fileWriter;
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
     * setting for every serial and movie, the number of views
     */
    public void setViews() {
        Movie movie;
        SerialInputData serial;
        for (User userIterator : this.parsingInput.getUsers()) {
            Iterator<Map.Entry<String, Integer>> itr =
                    userIterator.getHistory().entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<String, Integer> entry = itr.next();
                movie = Utils.returnMovie(entry.getKey(), this.parsingInput);
                if (movie != null) {
                    movie.incrementNoOfViews(entry.getValue());
                } else {
                    serial = Utils.returnSerial(entry.getKey(), this.parsingInput);
                    if (serial != null) {
                        serial.incrementNoOfViews(entry.getValue());
                    }
                }
            }
        }
    }
    /**
     * for every user, there is an occurrence vector with the shows that he saw
     */
    public void setVideosSeen() {
        Movie movie;
        SerialInputData serial;
        for (User iterator : this.parsingInput.getUsers()) {
            //size of appArray is size of serials+size of movies
            iterator.setAppArray(
                    this.parsingInput.getSerials().size() + this.parsingInput.getMovies().size());
            Iterator<Map.Entry<String, Integer>> itr = iterator.getHistory().entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<String, Integer> entry = itr.next();
                movie = Utils.returnMovie(entry.getKey(), this.parsingInput);
                if (movie != null) {
                    iterator.getAppArray().set(movie.getIndex(), 1);
                } else {
                    serial = Utils.returnSerial(entry.getKey(), this.parsingInput);
                    if (serial != null) {
                        iterator.getAppArray().set(serial.getIndex(), 1);
                    }
                }
            }
        }
    }
    /**
     * for every new view, I have to increment in the show, the parameter noOfViews
     * and also to set in the occurrence vector in the specific user
     */
    public org.json.simple.JSONObject result() throws IOException {
        User user = Utils.findusername(this.actiune.getUsername(), this.parsingInput);
        String message = null;
        user.getHistory().putIfAbsent(this.actiune.getTitle(), 0);
        user.getHistory()
                .put(this.actiune.getTitle(), user.getHistory().get(this.actiune.getTitle()) + 1);
        message = "success -> " + this.actiune.getTitle() + " was viewed with total views of "
                + user.getHistory().get(this.actiune.getTitle());
        //increment with 1 at the specific movie
        Movie movie;
        SerialInputData serial;
        movie = Utils.returnMovie(this.actiune.getTitle(), this.parsingInput);
        if (movie != null) {
            movie.incrementNoOfViews(1);
            if (user.getHistory().get(this.actiune.getTitle()) == 1) {
                user.getAppArray().set(movie.getIndex(), 1);
            }
        } else {
            serial = Utils.returnSerial(this.actiune.getTitle(), this.parsingInput);
            if (serial != null) {
                if (user.getHistory().get(this.actiune.getTitle()) == 1) {
                    user.getAppArray().set(serial.getIndex(), 1);
                }
                serial.incrementNoOfViews(1);
            }
        }
        return this.fileWriter.writeFile(this.actiune.getActionId(),
                null,
                message);
    }
}
