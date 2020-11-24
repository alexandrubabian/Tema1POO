package recommendation;

import fileio.ActionInputData;
import fileio.ShowInput;
import fileio.Writer;
import myclasses.ParsingInput;
import myclasses.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static utils.Utils.findusername;

public class Search extends RecomAbstract {

    private final List<String> videos;

    public Search(final ActionInputData actiune, final ParsingInput parsingInput,
                  final Writer fileWriter) {
        super(actiune, parsingInput, fileWriter);
        this.videos = new ArrayList<>();
    }
    /**
     * This function set the attribute videos which contains all the titles that are not seen
     * by the specified user and also that have the genre specified in each action
     * They are sorted by the average rating
     */
    public void setVideos() {
        List<ShowInput> copy = new ArrayList<>();
        User user = findusername(this.getActiune().getUsername(), this.getParsingInput());
        for (ShowInput iterator : this.getParsingInput().getMovies()) {
            if (user.getAppArray().get((iterator.getIndex())) == 0
                    && iterator.getGenres().contains(this.getActiune().getGenre())) {
                copy.add(iterator);
            }
        }
        for (ShowInput iterator : this.getParsingInput().getSerials()) {
            if (user.getAppArray().get((iterator.getIndex())) == 0
                    && iterator.getGenres().contains(this.getActiune().getGenre())) {
                copy.add(iterator);
            }
        }
        Collections.sort(copy, ShowInput.ascRatings.thenComparing(ShowInput.ascName));
        for (ShowInput iterator : copy) {
            this.videos.add(iterator.getTitle());
        }
    }
    /**
     * returning the JSONObject
     */
    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        User user = findusername(this.getActiune().getUsername(), this.getParsingInput());
        if (user.getSubscriptionType().equals("PREMIUM")) {
            this.setVideos();
            if (this.videos.size() > 0) {
                message = "SearchRecommendation result: " + this.videos.toString();
            } else {
                message = "SearchRecommendation cannot be applied!";
            }
        } else {
            message = "SearchRecommendation cannot be applied!";
        }
        return this.getFileWriter().writeFile(this.getActiune().getActionId(), null, message);
    }
}
