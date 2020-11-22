package Commands;

import fileio.*;
import myclasses.Movie;
import myclasses.ParsingInput;
import myclasses.User;

import java.io.IOException;

public class Rating {
    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    public Rating(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
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

    public User findusername (String username) {
        for (User user : this.parsingInput.getUsers())
            if (user.getUsername().equals(username)) {
                return user;
            }
        return null;
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

    public org.json.simple.JSONObject result() throws IOException {
        User user = findusername(this.actiune.getUsername());
        String message = null;
        Movie movie;
        SerialInputData serial;

        //message = "error -> " + this.actiune.getTitle() + " has been already rated";
        movie=returnMovie(this.actiune.getTitle());
            if (movie!=null) {
                if(user.getRatedVideos().contains(this.actiune.getTitle())){
                    message = "error -> " + this.actiune.getTitle() + " has been already rated";
                } else {
                //cazul in care este film nu serial
                    if (user.getHistory().containsKey(movie.getTitle())) {
                        movie.getRatings().add(this.actiune.getGrade());
                        movie.setRatingMediu();
                        user.getRatings().add(this.actiune.getGrade());
                        user.incrementRatings();
                        user.getRatedVideos().add(this.actiune.getTitle());
                        message = "success -> " + this.actiune.getTitle() + " was rated with " + this.actiune.getGrade() +
                                " by " + this.actiune.getUsername();
                    } else {
                        message = "error -> " + movie.getTitle() + " is not seen";
                    }
                }
            } else {
                serial = returnSerial(this.actiune.getTitle());
                if (serial.getSeasons().get(this.actiune.getSeasonNumber() - 1).
                        getUserThatRated().contains(user.getUsername())) {
                    message = "error -> " + this.actiune.getTitle() + " has been already rated";
                } else {
                    if (user.getHistory().containsKey(serial.getTitle())) {
                        serial.getSeasons().get(this.actiune.getSeasonNumber() - 1).getRatings().add(this.actiune.getGrade());
                        serial.getSeasons().get(this.actiune.getSeasonNumber() - 1).getUserThatRated().add(user.getUsername());
                        serial.setRatingMediu();
                        user.getRatings().add(this.actiune.getGrade());
                        user.incrementRatings();
                        message = "success -> " + this.actiune.getTitle() + " was rated with " + this.actiune.getGrade() +
                                " by " + this.actiune.getUsername();

                        user.getRatedVideos().add(this.actiune.getTitle());
                    } else {
                        message = "error -> " + serial.getTitle() + " is not seen";
                    }
                }
            }

        return this.fileWriter.writeFile(this.actiune.getActionId(),
                null,
                message);
    }
}