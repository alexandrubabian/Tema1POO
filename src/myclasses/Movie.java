package myclasses;

import fileio.ShowInput;

import java.util.ArrayList;

public final class Movie extends ShowInput {
    /**
     * Duration in minutes of a season
     */
    private final int duration;

    private final ArrayList<Double> ratings;

    public Movie(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratings=new ArrayList<>();
    }

    public ArrayList<Double> getRatings() {
        return ratings;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}