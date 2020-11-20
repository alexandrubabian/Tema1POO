package fileio;

import myclasses.Actor;
import myclasses.User;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * General information about show (video), retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public abstract class ShowInput {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;

    private final int duration;

    private int noOfViews;

    private int noOfFavorites;

    private Double ratingMediu;

    public ShowInput(final String title, final int year,
                     final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.noOfViews = 0;
        this.noOfFavorites = 0;
        this.duration = 0;
        this.ratingMediu = 0.0;
    }

    public int getDuration() {
        return duration;
    }

    public Double getRatingMediu() {
        return ratingMediu;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    public int getNoOfViews() {
        return noOfViews;
    }

    public void incrementNoOfViews(int noToIncrement) {
        noOfViews += noToIncrement;
    }

    public int getNoOfFavorites() { return noOfFavorites; }

    public void incrementNoOfFavorites(int noToIncrement) { noOfFavorites +=noToIncrement; }

    public abstract Double medieRating();

    public void setRatingMediu() {
        this.ratingMediu = this.medieRating();
    }

    public static Comparator<ShowInput> AscRatings = new Comparator<ShowInput>() {
        @Override
        public int compare(ShowInput o1, ShowInput o2) {
            double movieAvRating1 = o1.getRatingMediu();
            double movieAvRating2 = o2.getRatingMediu();

            return Double.compare(movieAvRating1, movieAvRating2);
        }
    };

    public static Comparator<ShowInput> DescRatings = new Comparator<ShowInput>() {
        @Override
        public int compare(ShowInput o1, ShowInput o2) {
            double movieAvRating1 = o1.getRatingMediu();
            double movieAvRating2 = o2.getRatingMediu();

            return Double.compare(movieAvRating2, movieAvRating1);
        }
    };

    public static Comparator<ShowInput> AscDuration = new Comparator<ShowInput>() {
        @Override
        public int compare(ShowInput o1, ShowInput o2) {
            int showDuration1 = o1.getDuration();
            int showDuration2 = o2.getDuration();

            return showDuration1 - showDuration2;
        }
    };

    public static Comparator<ShowInput> DescDuration = new Comparator<ShowInput>() {
        @Override
        public int compare(ShowInput o1, ShowInput o2) {
            int showDuration1 = o1.getDuration();
            int showDuration2 = o2.getDuration();

            return showDuration2 - showDuration1;
        }
    };


    public static Comparator<ShowInput> AscViews = new Comparator<ShowInput>() {
        @Override
        public int compare(ShowInput o1, ShowInput o2) {
            int showViews1 = o1.getNoOfViews();
            int showViews2 = o2.getNoOfViews();

            return showViews1 - showViews2;
        }
    };

    public static Comparator<ShowInput> DescViews = new Comparator<ShowInput>() {
        @Override
        public int compare(ShowInput o1, ShowInput o2) {
            int showViews1 = o1.getNoOfViews();
            int showViews2 = o2.getNoOfViews();

            return showViews2 - showViews1;
        }
    };

    public static Comparator<ShowInput> DescFavorites = new Comparator<ShowInput>() {
        @Override
        public int compare(ShowInput o1, ShowInput o2) {
            int showFav1 = o1.getNoOfFavorites();
            int showFav2 = o2.getNoOfFavorites();

            return showFav2 - showFav1;
        }
    };
}
