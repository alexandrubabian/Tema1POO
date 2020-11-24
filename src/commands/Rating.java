package commands;
import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.Writer;
import myclasses.Movie;
import myclasses.ParsingInput;
import myclasses.User;
import utils.Utils;

import java.io.IOException;

public final class Rating {
    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    public Rating(final ActionInputData actiune, final ParsingInput parsingInput,
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
     *for every show, i have to add the new rating in its parameters, to set the
     * average rating again and to increment the number of ratings
     */
    public org.json.simple.JSONObject result() throws IOException {
        User user = Utils.findusername(this.actiune.getUsername(), this.parsingInput);
        String message = null;
        Movie movie;
        SerialInputData serial;

        movie = Utils.returnMovie(this.actiune.getTitle(), this.parsingInput);
        if (movie != null) {
            if (user.getRatedVideos().contains(this.actiune.getTitle())) {
                message = "error -> " + this.actiune.getTitle() + " has been already rated";
            } else {
                //in case of movie
                if (user.getHistory().containsKey(movie.getTitle())) {
                    movie.getRatings().add(this.actiune.getGrade());
                    movie.setRatingMediu();
                    user.getRatings().add(this.actiune.getGrade());
                    user.incrementRatings();
                    user.getRatedVideos().add(this.actiune.getTitle());
                    message = "success -> " + this.actiune.getTitle() + " was rated with "
                            + this.actiune.getGrade() + " by " + this.actiune.getUsername();
                } else {
                    message = "error -> " + movie.getTitle() + " is not seen";
                }
            }
        } else {
            //in case of serial
            serial = Utils.returnSerial(this.actiune.getTitle(), this.parsingInput);
            if (serial.getSeasons().get(this.actiune.getSeasonNumber() - 1).
                    getUserThatRated().contains(user.getUsername())) {
                message = "error -> " + this.actiune.getTitle() + " has been already rated";
            } else {
                if (user.getHistory().containsKey(serial.getTitle())) {
                    serial.getSeasons().get(this.actiune.getSeasonNumber() - 1).getRatings()
                            .add(this.actiune.getGrade());
                    serial.getSeasons().get(this.actiune.getSeasonNumber() - 1).getUserThatRated()
                            .add(user.getUsername());
                    serial.setRatingMediu();
                    user.getRatings().add(this.actiune.getGrade());
                    user.incrementRatings();
                    message = "success -> " + this.actiune.getTitle() + " was rated with "
                            + this.actiune.getGrade() + " by " + this.actiune.getUsername();

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
