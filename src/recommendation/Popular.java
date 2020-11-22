package recommendation;

import fileio.ActionInputData;
import fileio.ShowInput;
import fileio.Writer;
import myclasses.Movie;
import myclasses.ParsingInput;
import myclasses.User;

import java.io.IOException;
import java.util.*;

public class Popular extends RecomAbstract{

    Map<String, Integer> genresSorted;

    List<ShowInput> notSeen;

    public Popular(ActionInputData actiune, ParsingInput parsingInput, Writer fileWriter) {
        super(actiune, parsingInput, fileWriter);
        this.genresSorted = new HashMap<String, Integer>();
        this.notSeen = new ArrayList<>();
    }

    public Map<String, Integer> getGenres() {
        return genresSorted;
    }

    public static HashMap<String,Integer> sortByValue(HashMap<String,Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue().compareTo(o1.getValue()));
            }
        });
        HashMap<String,Integer> aux = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> iterator : list) {
            aux.put(iterator.getKey(), iterator.getValue());
        }
        return aux;
    }

    public void setGenres() {
        User user = findusername(this.getActiune().getUsername());
        HashMap<String, Integer> genres = new HashMap<String, Integer>();
        for (ShowInput iterator : this.getParsingInput().getMovies()) {
            if (user.getAppArray().get(iterator.getIndex()) == 0) {
                this.notSeen.add(iterator);
                for (String genreIterator : iterator.getGenres()) {
                    genres.putIfAbsent(genreIterator, 0);
                    genres.put(genreIterator, genres.get(genreIterator) + iterator.getNoOfViews());
                }
            }
        }
        for (ShowInput iterator : this.getParsingInput().getSerials()) {
            if (user.getAppArray().get(iterator.getIndex()) == 0) {
                this.notSeen.add(iterator);
                for (String genreIterator : iterator.getGenres()) {
                    genres.putIfAbsent(genreIterator, 0);
                    genres.put(genreIterator, genres.get(genreIterator) + iterator.getNoOfViews());
                }
            }
            this.genresSorted = sortByValue(genres);
        }
    }

    public String findShowPopGenre() {
        this.setGenres();
        if(this.notSeen.size() > 0) {
            for (String key : this.genresSorted.keySet()) {
                for (ShowInput iterator : this.notSeen) {
                    if (iterator.getGenres().contains(key))
                        return iterator.getTitle();
                }
            }
        }
        return null;
    }
    public org.json.simple.JSONObject result() throws IOException {
        String message = null;
        User user = findusername(this.getActiune().getUsername());
        if(user.getSubscriptionType().equals("PREMIUM")) {
            if(this.findShowPopGenre() !=null) {
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
