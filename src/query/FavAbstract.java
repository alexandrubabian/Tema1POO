package query;

import fileio.ActionInputData;
import fileio.ShowInput;
import fileio.Writer;
import myclasses.ParsingInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public abstract class FavAbstract {
    private final ActionInputData actiune;

    private final ParsingInput parsingInput;

    private final Writer fileWriter;

    private final ArrayList<String> mostFav;

    public FavAbstract(final ActionInputData actiune, final ParsingInput parsingInput,
                       final Writer fileWriter) {
        this.actiune = actiune;
        this.parsingInput = parsingInput;
        this.fileWriter = fileWriter;
        this.mostFav = new ArrayList<>();
    }

    public final ActionInputData getActiune() {
        return actiune;
    }

    public final ParsingInput getParsingInput() {
        return parsingInput;
    }

    public final Writer getFileWriter() {
        return fileWriter;
    }

    public final ArrayList<String> getMostFav() {
        return mostFav;
    }
    /**
     * method to be decided in the classed that extends FavAbstract
     */
    public abstract void abstractMethod();
  /**
   * sorting the movies or the serials by the number of occurrence in the users favorite list, I
   * check for their year and genre if they are the same as the filers, by a big hardcode
   * and adding in my arraylist mostFav their title in a number that is specified
   * in each action or the entire list of shows if the size is lower than the number
   */
  public void setMostFav(final ArrayList<ShowInput> copy) {
        if (this.actiune.getSortType().equals("asc")) {
            Collections.sort(copy, ShowInput.ascFavorites.thenComparing(ShowInput.ascName));
        } else {
            Collections.sort(copy, ShowInput.descFavorites.thenComparing(ShowInput.descName));
        }
        int i = 0, j = 0;
        while (i < this.actiune.getNumber() && j < copy.size()) {
            if (copy.get(j).getNoOfFavorites() != 0) {
                if (this.actiune.getFilters().get(1).get(0) != null) {
                    if (copy.get(j).getGenres().containsAll(this.actiune.getFilters().get(1))) {
                        if (this.actiune.getFilters().get(0).get(0) == null) {
                            this.mostFav.add(copy.get(j).getTitle());
                            i++;
                        } else {
                            if (copy.get(j).getYear()
                                    == Integer.parseInt(actiune.getFilters().get(0).get(0))) {
                                this.mostFav.add(copy.get(j).getTitle());
                                i++;
                            }
                        }
                    }
                } else {
                    if (this.actiune.getFilters().get(0).get(0) == null) {
                        this.mostFav.add(copy.get(j).getTitle());
                        i++;
                    } else {
                        if (copy.get(j).getYear()
                                == Integer.parseInt(actiune.getFilters().get(0).get(0))) {
                            this.mostFav.add(copy.get(j).getTitle());
                            i++;
                        }
                    }
                }
            }
            j++;
        }
    }
    /**
     * returning the JSONObject
     */
    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        this.abstractMethod();
        message = "Query result: " + this.mostFav.toString();
        return this.fileWriter.writeFile(this.actiune.getActionId(), null, message);
    }
}
