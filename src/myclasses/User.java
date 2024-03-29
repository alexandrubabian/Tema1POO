package myclasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public final class User {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;

    //ratings done by the user
    private final ArrayList<Double> ratings;

    private final ArrayList<String> ratedVideos;

    private int numberOfRatings;
    /**
     * Occurrence array for
     */
    private ArrayList<Integer> appArray;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        this.ratings = new ArrayList<>();
        this.numberOfRatings = 0;
        this.ratedVideos = new ArrayList<>();
        this.appArray = new ArrayList<>();
    }

    public ArrayList<Integer> getAppArray() {
        return appArray;
    }

    public ArrayList<String> getRatedVideos() {
        return ratedVideos;
    }

    public ArrayList<Double> getRatings() {
        return ratings;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }
    /**
     * incrementing the number of ratings
     */
    public void incrementRatings() {
        numberOfRatings++;
    }

    public void setAppArray(final int size) {
        this.appArray = new ArrayList<Integer>(Collections.nCopies(size, 0));
    }

    public static Comparator<User> ascRating = new Comparator<User>() {
        @Override
        public int compare(final User o1, final User o2) {
            int userNoRating1 = o1.getNumberOfRatings();
            int userNoRating2 = o2.getNumberOfRatings();

            return userNoRating1 - userNoRating2;
        }
    };

    public static Comparator<User> descRating = new Comparator<User>() {
        @Override
        public int compare(final User o1, final User o2) {
            int userNoRating1 = o1.getNumberOfRatings();
            int userNoRating2 = o2.getNumberOfRatings();

            return userNoRating2 - userNoRating1;
        }
    };

    public static Comparator<User> ascName = new Comparator<User>() {
        @Override
        public int compare(final User o1, final User o2) {
            String userName1 = o1.getUsername();
            String userName2 = o2.getUsername();

            return userName1.compareTo(userName2);
        }
    };

    public static Comparator<User> descName = new Comparator<User>() {
        @Override
        public int compare(final User o1, final User o2) {
            String userName1 = o1.getUsername();
            String userName2 = o2.getUsername();

            return userName2.compareTo(userName1);
        }
    };

    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }
}
