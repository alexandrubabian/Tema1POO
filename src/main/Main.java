package main;

import commands.Favorite;
import commands.Rating;
import commands.View;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import myclasses.ParsingInput;
import myclasses.ParsingInputLoader;
import org.json.simple.JSONArray;
import query.AverageActor;
import query.FavMovies;
import query.FavSerials;
import query.LongMovie;
import query.LongSerial;
import query.MostViewedMov;
import query.MostViewedSer;
import query.QueryAwards;
import query.QueryFilter;
import query.QueryUsers;
import query.RatMovies;
import query.RatSerials;
import recommendation.BestUnseen;
import recommendation.Popular;
import recommendation.RecomFavorite;
import recommendation.Search;
import recommendation.Standard;

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
     *
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
        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //parsing the input in my own class
        ParsingInputLoader parsingInputLoader = new ParsingInputLoader(input);
        ParsingInput parsingInput = parsingInputLoader.parseAll();
        //setting for each movie and serial the number of views;
        View setup = new View(null, parsingInput, fileWriter);
        setup.setViews();

        //setting for each show how many times they appear in the favorite list
        Favorite setupFavorites = new Favorite(null, parsingInput, fileWriter);
        setupFavorites.setFavorites();

        for (int i = 0; i < parsingInput.getMovies().size(); i++) {
            //setting index for every movie
            parsingInput.getMovies().get(i).setIndex(i);
        }
        for (int i = 0; i < parsingInput.getSerials().size(); i++) {
            //setting duration for the serials
            parsingInput.getSerials().get(i).setDuration();
            //setting index for every serial
            parsingInput.getSerials().get(i).setIndex(parsingInput.getMovies().size() + i);
        }
        //setting the occurrence vector for the shows that every user have seen
        setup.setVideosSeen();
        for (ActionInputData actiune : parsingInput.getCommands()) {
            if (actiune.getActionType().equals("command")) {
                if (actiune.getType().equals("favorite")) {
                    Favorite favorite = new Favorite(actiune, parsingInput, fileWriter);
                    arrayResult.add(favorite.result());
                } else if (actiune.getType().equals("view")) {
                    View view = new View(actiune, parsingInput, fileWriter);
                    arrayResult.add(view.result());
                } else {
                    Rating rating = new Rating(actiune, parsingInput, fileWriter);
                    arrayResult.add(rating.result());
                }
            } else if (actiune.getActionType().equals("query")) {
                if (actiune.getObjectType().equals("users")) {
                    QueryUsers queryUsers = new QueryUsers(actiune, parsingInput, fileWriter);
                    arrayResult.add(queryUsers.result());
                } else if (actiune.getObjectType().equals("actors")) {
                    if (actiune.getCriteria().equals("average")) {
                        AverageActor averageActor =
                                new AverageActor(actiune, parsingInput, fileWriter);
                        arrayResult.add(averageActor.result());
                    } else if (actiune.getCriteria().equals("awards")) {
                        QueryAwards queryAwards =
                                new QueryAwards(actiune, parsingInput, fileWriter);
                        arrayResult.add(queryAwards.result());
                    } else {
                        QueryFilter queryFilter =
                                new QueryFilter(actiune, parsingInput, fileWriter);
                        arrayResult.add(queryFilter.result());
                    }
                } else if (actiune.getObjectType().equals("movies")) {
                    if (actiune.getCriteria().equals("most_viewed")) {
                        MostViewedMov mostViewedMov =
                                new MostViewedMov(actiune, parsingInput, fileWriter);
                        arrayResult.add(mostViewedMov.result());
                    } else if (actiune.getCriteria().equals("favorite")) {
                        FavMovies favMovies = new FavMovies(actiune, parsingInput, fileWriter);
                        arrayResult.add(favMovies.result());
                    } else if (actiune.getCriteria().equals("longest")) {
                        LongMovie longMovie = new LongMovie(actiune, parsingInput, fileWriter);
                        arrayResult.add(longMovie.result());
                    } else if (actiune.getCriteria().equals("ratings")) {
                        RatMovies ratMovies = new RatMovies(actiune, parsingInput, fileWriter);
                        arrayResult.add(ratMovies.result());
                    }
                } else if (actiune.getObjectType().equals("shows")) {
                    if (actiune.getCriteria().equals("most_viewed")) {
                        MostViewedSer mostViewedSer =
                                new MostViewedSer(actiune, parsingInput, fileWriter);
                        arrayResult.add(mostViewedSer.result());
                    } else if (actiune.getCriteria().equals("favorite")) {
                        FavSerials favSerials = new FavSerials(actiune, parsingInput, fileWriter);
                        arrayResult.add(favSerials.result());
                    } else if (actiune.getCriteria().equals("longest")) {
                        LongSerial longSerial = new LongSerial(actiune, parsingInput, fileWriter);
                        arrayResult.add(longSerial.result());
                    } else if (actiune.getCriteria().equals("ratings")) {
                        RatSerials ratSerials = new RatSerials(actiune, parsingInput, fileWriter);
                        arrayResult.add(ratSerials.result());
                    }
                }
            } else if (actiune.getActionType().equals("recommendation")) {
                if (actiune.getType().equals("standard")) {
                    Standard standard = new Standard(actiune, parsingInput, fileWriter);
                    arrayResult.add(standard.result());
                } else if (actiune.getType().equals("best_unseen")) {
                    BestUnseen bestUnseen = new BestUnseen(actiune, parsingInput, fileWriter);
                    arrayResult.add(bestUnseen.result());
                } else if (actiune.getType().equals("favorite")) {
                    RecomFavorite recomFavorite =
                            new RecomFavorite(actiune, parsingInput, fileWriter);
                    arrayResult.add(recomFavorite.result());
                } else if (actiune.getType().equals("search")) {
                    Search search = new Search(actiune, parsingInput, fileWriter);
                    arrayResult.add(search.result());
                } else if (actiune.getType().equals("popular")) {
                    Popular popular = new Popular(actiune, parsingInput, fileWriter);
                    arrayResult.add(popular.result());
                }
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
