package recommendation;

import fileio.ActionInputData;
import fileio.Writer;
import myclasses.ParsingInput;
import myclasses.User;

import java.io.IOException;

import static utils.Utils.findusername;

public class Standard extends RecomAbstract {

    public Standard(final ActionInputData actiune, final ParsingInput parsingInput,
                    final Writer fileWriter) {
        super(actiune, parsingInput, fileWriter);
    }
    /**
     * returning the JSONObject, which is the first show that the user specified in the action
     * had not seen
     */
    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        User user = findusername(this.getActiune().getUsername(), this.getParsingInput());
        int i;
        for (i = 0; i < user.getAppArray().size(); i++) {
            if (user.getAppArray().get(i) == 0) {
                break;
            }
        }
        if (i < this.getParsingInput().getMovies().size()) {
            //if it is a movie
            message = "StandardRecommendation result: "
                    + this.getParsingInput().getMovies().get(i).getTitle();
        } else if (i < (this.getParsingInput().getSerials().size()
                + this.getParsingInput().getMovies().size())) {
            //if it a serial
            message = "StandardRecommendation result: "
                    + this.getParsingInput().getSerials().get(i).getTitle();
        } else {
            message = "StandardRecommendation cannot be applied!";
        }
        return this.getFileWriter().writeFile(this.getActiune().getActionId(), null, message);
    }
}
