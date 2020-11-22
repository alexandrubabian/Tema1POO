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

public class Search extends RecomAbstract{

    private List<String> videos;

    public Search(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        super(actiune, parsingInput, fileWriter);
        this.videos = new ArrayList<>();
    }

    public void setVideos() {
        List<ShowInput> copy = new ArrayList<>();
        User user = findusername(this.getActiune().getUsername());
        for (ShowInput iterator : this.getParsingInput().getMovies()) {
            if(user.getAppArray().get((iterator.getIndex())) == 0 &&
            iterator.getGenres().contains(this.getActiune().getGenre())) {
                copy.add(iterator);
            }
        }
        for (ShowInput iterator : this.getParsingInput().getSerials()) {
            if(user.getAppArray().get((iterator.getIndex())) == 0 &&
                    iterator.getGenres().contains(this.getActiune().getGenre())) {
                copy.add(iterator);
            }
        }
        Collections.sort(copy,ShowInput.AscRatings.thenComparing(ShowInput.AscName));
        for (ShowInput iterator : copy) {
            this.videos.add(iterator.getTitle());
        }
    }

    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        User user =findusername(this.getActiune().getUsername());
        if(user.getSubscriptionType().equals("PREMIUM")) {
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
