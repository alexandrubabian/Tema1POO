package commands;

import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.Writer;
import myclasses.Movie;
import myclasses.ParsingInput;
import myclasses.User;
import utils.Utils;

import java.io.IOException;


public final class Favorite {

    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    public Favorite(final ActionInputData actiune, final ParsingInput parsingInput,
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
     * setting for every movie and serial the number of apparitions in favorite list of users
     */
    public void setFavorites() {
        Movie movie;
        SerialInputData serial;
        for (User userIterator : this.parsingInput.getUsers()) {
            for (String stringIterator : userIterator.getFavoriteMovies()) {
                movie = Utils.returnMovie(stringIterator, this.parsingInput);
                if (movie != null) {
                    movie.incrementNoOfFavorites(1);
                } else {
                    serial = Utils.returnSerial(stringIterator, this.parsingInput);
                    if (serial != null) {
                        serial.incrementNoOfFavorites(1);
                    }
                }
            }
        }
    }
    /**
     * checking if the show is in a favorite list, is seen, or if i have to add it
     * in the favorite list of the user
     */
    public org.json.simple.JSONObject result() throws IOException {
        Movie movie;
        SerialInputData serial;
        User user = Utils.findusername(this.actiune.getUsername(), this.parsingInput);
        String message = null;
        if (user.getFavoriteMovies().contains(this.actiune.getTitle())) {
            message = "error -> " + this.actiune.getTitle() + " is already in favourite list";
        } else {
            if (user.getHistory().containsKey(this.actiune.getTitle())) {
                message = "success -> " + this.actiune.getTitle() + " was added as favourite";
                user.getFavoriteMovies().add(this.actiune.getTitle());
                movie = Utils.returnMovie(this.actiune.getTitle(), this.parsingInput);
                if (movie != null) {
                    movie.incrementNoOfFavorites(1);
                } else {
                    serial = Utils.returnSerial(this.actiune.getTitle(), this.parsingInput);
                    if (serial != null) {
                        serial.incrementNoOfFavorites(1);
                    }
                }
            } else {
                message = "error -> " + this.actiune.getTitle() + " is not seen";
            }
        }

        return this.fileWriter.writeFile(this.actiune.getActionId(),
                null,
                message);
    }
}
