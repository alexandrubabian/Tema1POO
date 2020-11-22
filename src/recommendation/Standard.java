package recommendation;

import fileio.ActionInputData;
import fileio.Writer;
import myclasses.ParsingInput;
import myclasses.User;

import java.io.IOException;

public class Standard extends RecomAbstract{

    public Standard(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        super(actiune, parsingInput, fileWriter);
    }

    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        User user = findusername(this.getActiune().getUsername());
        int i;
        for (i = 0; i < user.getAppArray().size(); i++) {
            if (user.getAppArray().get(i) == 0) {
                break;
            }
        }
        if (i < this.getParsingInput().getMovies().size()) {
            //cazul in care este movie primul nevizionat
            message = "StandardRecommendation result: " + this.getParsingInput().getMovies().get(i).getTitle();
        } else if(i < (this.getParsingInput().getSerials().size()+this.getParsingInput().getMovies().size())){
            message = "StandardRecommendation result: " + this.getParsingInput().getSerials().get(i).getTitle();
        } else {
            message = "StandardRecommendation cannot be applied!";
        }
        return this.getFileWriter().writeFile(this.getActiune().getActionId(), null, message);
    }
}
