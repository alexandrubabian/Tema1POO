package myclasses;

import fileio.ShowInput;

import java.util.ArrayList;

public final class Movie extends ShowInput {
    /**
     * Duration in minutes of a season
     */
    private final ArrayList<Double> ratings;

    public Movie(final String title, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int year,
                 final int duration) {
        super(title, year, cast, genres);
        this.setDuration(duration);
        this.ratings = new ArrayList<>();
    }


    public ArrayList<Double> getRatings() {
        return ratings;
    }
    /**
     * Calculating the averageRating for each movie
     */
    public Double medieRating() {
        Double sum;
        sum = this.ratings.stream().mapToDouble(a -> a).sum();
        Double medie = 0.0;
        if (this.ratings.size() != 0) {
            medie = sum / this.ratings.size();
        }
        return medie;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + super.getDuration() + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
