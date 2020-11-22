package recommendation;

import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.ShowInput;
import fileio.Writer;
import myclasses.Movie;
import myclasses.ParsingInput;
import myclasses.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Best_Unseen extends RecomAbstract {
    public Best_Unseen(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        super(actiune, parsingInput, fileWriter);
    }

    public String setNotSeenRating() {
        List<ShowInput> copy = new ArrayList<>();
        User user = findusername(this.getActiune().getUsername());
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
        Collections.sort(copy, ShowInput.DescRatings);
        int iterator = 0;
        if(copy.size() > 0) {
            while (copy.size() > (iterator+1) &&
                    copy.get(iterator).getRatingMediu().equals(copy.get(iterator+1).getRatingMediu())){
                iterator++;
            }
            copy.subList(iterator+1,copy.size()).clear();
            Collections.sort(copy,ShowInput.AscIndex);
            return copy.get(0).getTitle();
        }
        return "nu ar fi trebuit sa ajunga aici vreodata";

    }

    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        User user = findusername(this.getActiune().getUsername());
        if(user.getAppArray().contains(0)) {
            message = "BestRatedUnseenRecommendation result: " + this.setNotSeenRating();
        } else {
            message = "BestRatedUnseenRecommendation cannot be applied!";
        }
        return this.getFileWriter().writeFile(this.getActiune().getActionId(), null, message);

    }
}
