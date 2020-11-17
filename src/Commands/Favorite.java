package Commands;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import myclasses.ParsingInput;
import myclasses.User;
import org.json.simple.JSONObject;

import java.io.IOException;


public class Favorite {

    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    public Favorite(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        this.actiune=actiune;
        this.parsingInput=parsingInput;
        this.fileWriter=fileWriter;//asta l-am adaugat pentru a putea folosi apelul de writeFile
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

    public org.json.simple.JSONObject result() throws IOException {
        User user = findusername(this.actiune.getUsername());
        String message = null;
        if (user.getFavoriteMovies().contains(this.actiune.getTitle())) {
            message = "error -> " + this.actiune.getTitle() + " is already in favourite list";
        }
        else {
            if(user.getHistory().containsKey(this.actiune.getTitle())) {
                message = "success -> " + this.actiune.getTitle() + " was added as favourite";
                user.getFavoriteMovies().add(this.actiune.getTitle());
            }
            else {
                message = "error -> " + this.actiune.getTitle() + " is not seen";
            }
        }

        return this.fileWriter.writeFile(this.actiune.getActionId(),
                null,
                message);
        //I return the JSONObject that is created with the method writeFile from writer class
    }
}
