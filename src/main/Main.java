package main;

import Commands.Favorite;
import Commands.Rating;
import Commands.View;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import myclasses.ParsingInput;
import myclasses.ParsingInputLoader;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import query.AverageActor;
import query.QueryAwards;
import query.QueryUsers;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();
        //deci input va avea actorsData,usersData,commandsData,moviesData,serialsData
        //fiecare cu tipul lui aferent care este descris in fileio

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //trecere in clasele mele
        ParsingInputLoader parsingInputLoader = new ParsingInputLoader(input);
        ParsingInput parsingInput = parsingInputLoader.parseAll();
        //gata
        for (ActionInputData actiune : parsingInput.getCommands()) {
            if (actiune.getActionType().equals("command")) {
                if (actiune.getType().equals("favorite")) {
                    //System.out.println("favorite");
                    //am adaugat si fileWriter pt functionalitatile lui Writer
                    Favorite favorite = new Favorite(actiune, parsingInput, fileWriter);
                    arrayResult.add(favorite.result());
                    //tine minte ca favorite.result returneaza un JSONObject, de forma id plus message
                } else if (actiune.getType().equals("view")) {
                    //System.out.println("view");
                    View view = new View(actiune, parsingInput, fileWriter);
                    arrayResult.add(view.result());
                } else {
                    Rating rating=new Rating(actiune, parsingInput, fileWriter);
                    arrayResult.add(rating.result());
                }
            } else if (actiune.getActionType().equals("query")) {
                if (actiune.getObjectType().equals("users")) {
                    QueryUsers queryUsers = new QueryUsers(actiune, parsingInput, fileWriter);
                    arrayResult.add(queryUsers.result());
                } else {
                    if(actiune.getObjectType().equals("actors")) {
                        if(actiune.getCriteria().equals("average")) {
                            AverageActor averageActor = new AverageActor(actiune, parsingInput, fileWriter);
                            arrayResult.add(averageActor.result());
                        } else if (actiune.getCriteria().equals("awards")){
                            QueryAwards queryAwards = new QueryAwards(actiune, parsingInput, fileWriter);
                            arrayResult.add(queryAwards.result());
                        }
                    }
                }

            }
        //TODO add here the entry point to your implementation
        //arrayResult.add(JSONObject)

            //System.out.println(arrayResult);

    }
        fileWriter.closeJSON(arrayResult);
}
}
