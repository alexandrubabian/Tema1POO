package recommendation;

import fileio.ActionInputData;
import fileio.ShowInput;
import fileio.Writer;
import myclasses.ParsingInput;
import myclasses.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static utils.Utils.findusername;

public final class Popular extends RecomAbstract {

    private Map<String, Integer> genresSorted;

    private List<ShowInput> notSeen;

    public Popular(final ActionInputData actiune, final ParsingInput parsingInput,
                   final Writer fileWriter) {
        super(actiune, parsingInput, fileWriter);
        this.genresSorted = new HashMap<String, Integer>();
        this.notSeen = new ArrayList<>();
    }

    public Map<String, Integer> getGenresSorted() {
        return genresSorted;
    }

    public List<ShowInput> getNotSeen() {
        return notSeen;
    }

    public Map<String, Integer> getGenres() {
        return genresSorted;
    }
    /**
     * sort the genres by the number of views
     */
    public static HashMap<String, Integer> sortByValue(final HashMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(final Map.Entry<String, Integer> o1,
                               final Map.Entry<String, Integer> o2) {
                return (o2.getValue().compareTo(o1.getValue()));
            }
        });
        HashMap<String, Integer> aux = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> iterator : list) {
            aux.put(iterator.getKey(), iterator.getValue());
        }
        return aux;
    }
    /**
     * setting the attribute notSeen with all the shows that are not seen bu the specified user
     * also setting and sorting the attribute genresSorted by the number of views of each genre
     */
    public void setGenres() {
        User user = findusername(this.getActiune().getUsername(), this.getParsingInput());
        HashMap<String, Integer> genres = new HashMap<String, Integer>();
        for (ShowInput iterator : this.getParsingInput().getMovies()) {
            for (String genreIterator : iterator.getGenres()) {
                genres.putIfAbsent(genreIterator, 0);
                genres.put(genreIterator, genres.get(genreIterator) + iterator.getNoOfViews());
            }
            if (user.getAppArray().get(iterator.getIndex()) == 0) {
                this.notSeen.add(iterator);
            }
        }
        for (ShowInput iterator : this.getParsingInput().getSerials()) {
            for (String genreIterator : iterator.getGenres()) {
                genres.putIfAbsent(genreIterator, 0);
                genres.put(genreIterator, genres.get(genreIterator) + iterator.getNoOfViews());
            }
            if (user.getAppArray().get(iterator.getIndex()) == 0) {
                this.notSeen.add(iterator);
            }
            this.genresSorted = sortByValue(genres);
        }
    }
    /**
     * this function find the first video unseen from the most popular genre
     */
    public String findShowPopGenre() {
        this.setGenres();
        if (this.notSeen.size() > 0) {
            for (String key : this.genresSorted.keySet()) {
                for (ShowInput iterator : this.notSeen) {
                    if (iterator.getGenres().contains(key)) {
                        return iterator.getTitle();
                    }
                }
            }
        }
        return null;
    }
    /**
     * returning the JSONObject
     */
    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        User user = findusername(this.getActiune().getUsername(), this.getParsingInput());
        if (user.getSubscriptionType().equals("PREMIUM")) {
            if (this.findShowPopGenre() != null) {
                message = "PopularRecommendation result: " + this.findShowPopGenre();
            } else {
                message = "PopularRecommendation cannot be applied!";
            }
        } else {
            message = "PopularRecommendation cannot be applied!";
        }
        return this.getFileWriter().writeFile(this.getActiune().getActionId(), null, message);
    }
}
