package recommendation;

import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.Writer;
import myclasses.Movie;
import myclasses.ParsingInput;
import myclasses.User;

public abstract class RecomAbstract {

    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    public RecomAbstract(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        this.actiune = actiune;
        this.parsingInput = parsingInput;
        this.fileWriter = fileWriter;
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

    public User findusername (String username) {
        for (User user : this.parsingInput.getUsers())
            if (user.getUsername().equals(username)) {
                return user;
            }
        return null;
    }
    public Movie returnMovie(String title) {
        for (Movie movie : this.getParsingInput().getMovies())
            if(movie.getTitle().equals(title)) {
                return movie;
            }
        return null;
    }
    public SerialInputData returnSerial(String title) {
        for (SerialInputData serial : this.getParsingInput().getSerials())
            if(serial.getTitle().equals(title)) {
                return serial;
            }
        return null;
    }
}
