package fileio;

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

    private int duration;

    private int noOfViews;

    private int noOfFavorites;

    private Double ratingMediu;

    private int index;

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
        this.index = 0;
    }

    public final void setDuration(final int duration) {
        this.duration = duration;
    }

    public final int getIndex() {
        return index;
    }
    /**
     * Method that is overrided in SerialInputData
     */
    public int getDuration() {
        return duration;
    }

    public final Double getRatingMediu() {
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

    public final int getNoOfViews() {
        return noOfViews;
    }
    /**
     * incrementing the number of views of a show
     */
    public void incrementNoOfViews(final int noToIncrement) {
        noOfViews += noToIncrement;
    }

    public final int getNoOfFavorites() {
        return noOfFavorites;
    }


    public final void setIndex(final int index) {
        this.index = index;
    }
    /**
     * incrementing the number of times that a show is found in a user list
     */
    public final void incrementNoOfFavorites(final int noToIncrement) {
        noOfFavorites += noToIncrement;
    }
    /**
     * to be decided in the movie or serialInputData
     */
    public abstract Double medieRating();
    /**
     * setter of average rating
     */
    public final void setRatingMediu() {
        this.ratingMediu = this.medieRating();
    }

    public static Comparator<ShowInput> ascRatings = new Comparator<ShowInput>() {
        @Override
        public int compare(final ShowInput o1, final ShowInput o2) {
            double movieAvRating1 = o1.getRatingMediu();
            double movieAvRating2 = o2.getRatingMediu();

            return Double.compare(movieAvRating1, movieAvRating2);
        }
    };

    public static Comparator<ShowInput> descRatings = new Comparator<ShowInput>() {
        @Override
        public int compare(final ShowInput o1, final ShowInput o2) {
            double movieAvRating1 = o1.getRatingMediu();
            double movieAvRating2 = o2.getRatingMediu();

            return Double.compare(movieAvRating2, movieAvRating1);
        }
    };

    public static Comparator<ShowInput> ascDuration = new Comparator<ShowInput>() {
        @Override
        public int compare(final ShowInput o1, final ShowInput o2) {
            int showDuration1 = o1.getDuration();
            int showDuration2 = o2.getDuration();

            return showDuration1 - showDuration2;
        }
    };

    public static Comparator<ShowInput> descDuration = new Comparator<ShowInput>() {
        @Override
        public int compare(final ShowInput o1, final ShowInput o2) {
            int showDuration1 = o1.getDuration();
            int showDuration2 = o2.getDuration();

            return showDuration2 - showDuration1;
        }
    };

    public static Comparator<ShowInput> ascIndex = new Comparator<ShowInput>() {
        @Override
        public int compare(final ShowInput o1, final ShowInput o2) {
            int showIndex1 = o1.getIndex();
            int showIndex2 = o2.getIndex();

            return showIndex1 - showIndex2;
        }
    };

    public static Comparator<ShowInput> ascViews = new Comparator<ShowInput>() {
        @Override
        public int compare(final ShowInput o1, final ShowInput o2) {
            int showViews1 = o1.getNoOfViews();
            int showViews2 = o2.getNoOfViews();

            return showViews1 - showViews2;
        }
    };

    public static Comparator<ShowInput> descViews = new Comparator<ShowInput>() {
        @Override
        public int compare(final ShowInput o1, final ShowInput o2) {
            int showViews1 = o1.getNoOfViews();
            int showViews2 = o2.getNoOfViews();

            return showViews2 - showViews1;
        }
    };

    public static Comparator<ShowInput> descFavorites = new Comparator<ShowInput>() {
        @Override
        public int compare(final ShowInput o1, final ShowInput o2) {
            int showFav1 = o1.getNoOfFavorites();
            int showFav2 = o2.getNoOfFavorites();

            return showFav2 - showFav1;
        }
    };

    public static Comparator<ShowInput> ascFavorites = new Comparator<ShowInput>() {
        @Override
        public int compare(final ShowInput o1, final ShowInput o2) {
            int showFav1 = o1.getNoOfFavorites();
            int showFav2 = o2.getNoOfFavorites();

            return showFav1 - showFav2;
        }
    };

    public static Comparator<ShowInput> ascName = new Comparator<ShowInput>() {
        @Override
        public int compare(final ShowInput o1, final ShowInput o2) {
            String showName1 = o1.getTitle();
            String showName2 = o2.getTitle();

            return showName1.compareTo(showName2);
        }
    };

    public static Comparator<ShowInput> descName = new Comparator<ShowInput>() {
        @Override
        public int compare(final ShowInput o1, final ShowInput o2) {
            String showName1 = o1.getTitle();
            String showName2 = o2.getTitle();

            return showName2.compareTo(showName1);
        }
    };
}
