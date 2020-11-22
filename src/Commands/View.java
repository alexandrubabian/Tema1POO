package Commands;

import fileio.*;
import myclasses.Movie;
import myclasses.ParsingInput;
import myclasses.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class View {
    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    public View(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
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

    public void setViews() {
        Movie movie;
        SerialInputData serial;
        for (User userIterator : this.parsingInput.getUsers()) {
            Iterator<Map.Entry<String, Integer>> itr = userIterator.getHistory().entrySet().iterator();
            while(itr.hasNext()) {
                Map.Entry<String, Integer> entry = itr.next();
                movie = returnMovie(entry.getKey());
                if (movie !=null) {
                    movie.incrementNoOfViews(entry.getValue());
                } else {
                    serial = returnSerial(entry.getKey());
                    if (serial != null) {
                        serial.incrementNoOfViews(entry.getValue());
                    }
                }
            }
        }
    }

    public void setVideosSeen() {
        Movie movie;
        SerialInputData serial;
        for (User iterator : this.parsingInput.getUsers()) {
            //definesc dimensiunea lui appArray
            iterator.setAppArray(this.parsingInput.getSerials().size()+this.parsingInput.getMovies().size());
            Iterator<Map.Entry<String, Integer>> itr = iterator.getHistory().entrySet().iterator();
            while(itr.hasNext()) {
                Map.Entry<String, Integer> entry = itr.next();
                movie = returnMovie(entry.getKey());
                if (movie !=null) {
                    iterator.getAppArray().set(movie.getIndex(),1);
                } else {
                    serial = returnSerial(entry.getKey());
                    if (serial != null) {
                        iterator.getAppArray().set(serial.getIndex(),1);
                    }
                }
            }
        }
    }


    public org.json.simple.JSONObject result() throws IOException {
        User user = findusername(this.actiune.getUsername());
        String message = null;
        user.getHistory().putIfAbsent(this.actiune.getTitle(),0);
        //ai putea sa faci niste constante pt denumirile astea lungi
        user.getHistory().put(this.actiune.getTitle(),user.getHistory().get(this.actiune.getTitle())+1);
        message = "success -> " + this.actiune.getTitle() + " was viewed with total views of " +
                user.getHistory().get(this.actiune.getTitle());
        //increment with 1 at the specific movie
        Movie movie;
        SerialInputData serial;
        movie = returnMovie(this.actiune.getTitle());
        if (movie !=null) {
            movie.incrementNoOfViews(1);
            if(user.getHistory().get(this.actiune.getTitle()) == 1) {
                user.getAppArray().set(movie.getIndex(),1);
            }
        } else {
            serial = returnSerial(this.actiune.getTitle());
            if (serial != null) {
                if(user.getHistory().get(this.actiune.getTitle()) == 1) {
                    user.getAppArray().set(serial.getIndex(),1);
            }
                serial.incrementNoOfViews(1);
            }
        }
        return this.fileWriter.writeFile(this.actiune.getActionId(),
                null,
                message);
    }
}
