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

public class BestUnseen extends RecomAbstract {
    public BestUnseen(final ActionInputData actiune, final ParsingInput parsingInput,
                      final Writer fileWriter) {
        super(actiune, parsingInput, fileWriter);
    }
    /**
     * this function sort all the shows that a users had not seen descending by their ratings
     * and then select the first one from the arrayList
     */
    public String setNotSeenRating() {
        List<ShowInput> copy = new ArrayList<>();
        User user = findusername(this.getActiune().getUsername(), this.getParsingInput());
        for (ShowInput iterator : this.getParsingInput().getMovies()) {
            if (user.getAppArray().get(iterator.getIndex()) == 0) {
                copy.add(iterator);
            }
        }
        for (ShowInput iterator : this.getParsingInput().getSerials()) {
            if (user.getAppArray().get(iterator.getIndex()) == 0) {
                copy.add(iterator);
            }
        }
        Collections.sort(copy, ShowInput.descRatings.thenComparing(ShowInput.ascIndex));
        if (copy.size() > 0) {
            return copy.get(0).getTitle();
        }
        return "nu ar fi trebuit sa ajunga aici vreodata";

    }
    /**
     * returning the JSONObject
     */
    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        User user = findusername(this.getActiune().getUsername(), this.getParsingInput());
        if (user.getAppArray().contains(0)) {
            message = "BestRatedUnseenRecommendation result: " + this.setNotSeenRating();
        } else {
            message = "BestRatedUnseenRecommendation cannot be applied!";
        }
        return this.getFileWriter().writeFile(this.getActiune().getActionId(), null, message);

    }
}
