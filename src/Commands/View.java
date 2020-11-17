package Commands;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import myclasses.ParsingInput;
import myclasses.User;

import java.io.IOException;

public class View {
    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    public View(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
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

    public org.json.simple.JSONObject result() throws IOException {
        User user = findusername(this.actiune.getUsername());
        String message = null;
        user.getHistory().putIfAbsent(this.actiune.getTitle(),0);
        //ai putea sa faci niste constante pt denumirile astea lungi
        user.getHistory().put(this.actiune.getTitle(),user.getHistory().get(this.actiune.getTitle())+1);
        message = "success -> " + this.actiune.getTitle() + " was viewed with total views of " +
                user.getHistory().get(this.actiune.getTitle());

        return this.fileWriter.writeFile(this.actiune.getActionId(),
                null,
                message);
    }
}
