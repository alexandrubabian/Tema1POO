package myclasses;

import actor.ActorsAwards;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public final class Actor {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;

    private Double averageRating;

    private int noOfawards;

    public Actor(final String name, final String careerDescription,
                          final ArrayList<String> filmography,
                          final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.averageRating=0.0;
        for (int iterator : this.awards.values()) {
            this.noOfawards += iterator;
        }
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public int getNoOfawards() {
        return noOfawards;
    }

    public Movie returnMovie(String title, ParsingInput parsingInput1) {
        for (Movie movie : parsingInput1.getMovies()) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }
    public SerialInputData returnSerial(String title, ParsingInput parsingInput1) {
        for (SerialInputData serial : parsingInput1.getSerials()) {
            if (serial.getTitle().equals(title)) {
                return serial;
            }
        }
        return null;
    }

    public void setAverageRating(ParsingInput parsingInput) {
        Movie movie;
        SerialInputData serial;
        int noActiveVideos = 0;
        if (this.filmography.size() == 0){
            this.averageRating = 0.0;
        } else {
            for (String iterator : this.filmography) {
                movie = returnMovie(iterator, parsingInput);
                if (movie != null && movie.medieRating() != 0) {
                    noActiveVideos++;
                    this.averageRating += movie.medieRating();
                } else {
                    serial = returnSerial(iterator, parsingInput);
                    if (serial != null && serial.medieRating() != 0) {
                        this.averageRating += serial.medieRating();
                        noActiveVideos++;
                    }
                }
            }
            this.averageRating = this.averageRating / noActiveVideos;
        }
    }

    public static Comparator<Actor> AscAverageRating = new Comparator<Actor>() {
        @Override
        public int compare(Actor o1, Actor o2) {
            double actorAvRating1 = o1.getAverageRating();
            double actorAvRating2 = o2.getAverageRating();

            return Double.compare(actorAvRating1, actorAvRating2);
        }
    };

    public static Comparator<Actor> DescAverageRating = new Comparator<Actor>() {
        @Override
        public int compare(Actor o1, Actor o2) {
            double actorAvRating1 = o1.getAverageRating();
            double actorAvRating2 = o2.getAverageRating();

            return Double.compare(actorAvRating2, actorAvRating1);
        }
    };

    public static Comparator<Actor> AscName = new Comparator<Actor>() {
        @Override
        public int compare(Actor o1, Actor o2) {
            String actorName1 = o1.getName();
            String actorName2 = o2.getName();

            return actorName1.compareTo(actorName2);
        }
    };

    public static Comparator<Actor> DescName = new Comparator<Actor>() {
        @Override
        public int compare(Actor o1, Actor o2) {
            String actorName1 = o1.getName();
            String actorName2 = o2.getName();

            return actorName2.compareTo(actorName1);
        }
    };

    public static Comparator<Actor> AscNoAwards = new Comparator<Actor>() {
        @Override
        public int compare(Actor o1, Actor o2) {
            int actorNoAwards1 = o1.getNoOfawards();
            int actorNoAwards2 = o2.getNoOfawards();

            return actorNoAwards1 - actorNoAwards2;
        }
    };

    public static Comparator<Actor> DescNoAwards = new Comparator<Actor>() {
        @Override
        public int compare(Actor o1, Actor o2) {
            int actorNoAwards1 = o1.getNoOfawards();
            int actorNoAwards2 = o2.getNoOfawards();

            return actorNoAwards2 - actorNoAwards1;
        }
    };

    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}
