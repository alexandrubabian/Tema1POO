package Commands;

import fileio.*;
import myclasses.Movie;
import myclasses.ParsingInput;
import myclasses.User;
import org.json.simple.JSONObject;

import java.io.IOException;


public class Favorite {

    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    public Favorite(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        this.actiune=actiune;
        this.parsingInput=parsingInput;
        this.fileWriter=fileWriter;//asta l-am adaugat pentru a putea folosi apelul de writeFile
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

    public Movie returnMovie(String title) {
        for (Movie movie : this.parsingInput.getMovies())
            if(movie.getTitle().equals(title)) {
                return movie;
            }
        return null;
    }
    public SerialInputData returnSerial(String title) {
        for (SerialInputData serial : this.parsingInput.getSerials())
            if(serial.getTitle().equals(title)) {
                return serial;
            }
        return null;
    }

    public User findusername (String username) {
        for (User user : this.parsingInput.getUsers())
            if (user.getUsername().equals(username)) {
                return user;
            }
        return null;
    }

    public void setFavorites() {
        //aici ai de completat precum in views, de asemenea sa faci un comparator si in showInput
        Movie movie;
        SerialInputData serial;
        for (User userIterator : this.parsingInput.getUsers()) {
            for (String stringIterator : userIterator.getFavoriteMovies()) {
                movie = returnMovie(stringIterator);
                if(movie != null) {
                    movie.incrementNoOfFavorites(1);
                } else {
                    serial = returnSerial(stringIterator);
                    if(serial != null) {
                        serial.incrementNoOfFavorites(1);
                    }
                }
            }
        }
    }

    public org.json.simple.JSONObject result() throws IOException {
        Movie movie;
        SerialInputData serial;
        User user = findusername(this.actiune.getUsername());
        String message = null;
        if (user.getFavoriteMovies().contains(this.actiune.getTitle())) {
            message = "error -> " + this.actiune.getTitle() + " is already in favourite list";
        }
        else {
            if(user.getHistory().containsKey(this.actiune.getTitle())) {
                message = "success -> " + this.actiune.getTitle() + " was added as favourite";
                user.getFavoriteMovies().add(this.actiune.getTitle());
                //!!!!poate poti face o singura metoda doar din asta;
                movie = returnMovie(this.actiune.getTitle());
                if(movie != null) {
                    movie.incrementNoOfFavorites(1);
                } else {
                    serial = returnSerial(this.actiune.getTitle());
                    if(serial != null) {
                        serial.incrementNoOfFavorites(1);
                    }
                }
            }
            else {
                message = "error -> " + this.actiune.getTitle() + " is not seen";
            }
        }

        return this.fileWriter.writeFile(this.actiune.getActionId(),
                null,
                message);
        //I return the JSONObject that is created with the method writeFile from writer class
    }
}
