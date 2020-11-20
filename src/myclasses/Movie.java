package myclasses;

import fileio.ShowInput;
import query.MostViewed_mov;

import java.util.ArrayList;
import java.util.Comparator;

public final class Movie extends ShowInput {
    /**
     * Duration in minutes of a season
     */
    private final int duration;

    private final ArrayList<Double> ratings;

    //private Double ratingMediu;

   // private int noOfViews;

    public Movie(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratings = new ArrayList<>();
        //this.ratingMediu = 0.0;
        //this.noOfViews = 0;
    }


    public ArrayList<Double> getRatings() {
        return ratings;
    }

    public int getDuration() {
        return duration;
    }

//    public Double getRatingMediu() {
//        return ratingMediu;
//    }

//    public void incrementNoOfViews(int noToIncrement) {
//        getNoOfViews() += noToIncrement;
//    }

    public Double medieRating() {
        Double sum;
        sum = this.ratings.stream().mapToDouble(a -> a).sum();
        Double medie = 0.0;
        if (this.ratings.size() != 0){
            medie=sum/this.ratings.size();
        }
        return medie;
    }

    //vezi ca astea sunt facute pt alt subpunct, iti vei da seama
    public static Comparator<Movie> AscAverageRating = new Comparator<Movie>() {
        @Override
        public int compare(Movie o1, Movie o2) {
            double movieAvRating1 = o1.getRatingMediu();
            double movieAvRating2 = o2.getRatingMediu();

            return Double.compare(movieAvRating1, movieAvRating2);
        }
    };

    public static Comparator<Movie> DescAverageRating = new Comparator<Movie>() {
        @Override
        public int compare(Movie o1, Movie o2) {
            double movieAvRating1 = o1.getRatingMediu();
            double movieAvRating2 = o2.getRatingMediu();

            return Double.compare(movieAvRating2, movieAvRating1);
        }
    };

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
