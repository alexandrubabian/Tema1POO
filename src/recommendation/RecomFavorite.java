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

public class RecomFavorite extends RecomAbstract {
    public RecomFavorite(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        super(actiune, parsingInput, fileWriter);
    }

    public String setFavorite() {
        List<ShowInput> copy = new ArrayList<>();
        User user = findusername(this.getActiune().getUsername());
        for (ShowInput iterator : this.getParsingInput().getMovies()) {
            if (user.getAppArray().get(iterator.getIndex()) == 0 &&
                    iterator.getNoOfFavorites() > 0) {
                copy.add(iterator);
            }
        }
        for (ShowInput iterator : this.getParsingInput().getSerials()) {
            if (user.getAppArray().get(iterator.getIndex()) == 0 &&
                    iterator.getNoOfFavorites() > 0) {
                copy.add(iterator);
            }
        }
        Collections.sort(copy, ShowInput.DescFavorites.thenComparing(ShowInput.AscIndex));
        if(copy.size()>0){
//
            return copy.get(0).getTitle();
        }
        return "din pacate nu avem chestii favorite";
    }

    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        User user = findusername(this.getActiune().getUsername());
        if(user.getSubscriptionType().equals("PREMIUM")) {
            if (!this.setFavorite().equals("din pacate nu avem chestii favorite")) {
                message = "FavoriteRecommendation result: " + this.setFavorite();
            } else {
                message = "FavoriteRecommendation cannot be applied!";
            }
        } else {
            message = "FavoriteRecommendation cannot be applied!";
        }
            return this.getFileWriter().writeFile(this.getActiune().getActionId(), null, message);
    }
}
