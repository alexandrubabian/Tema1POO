package fileio;

import entertainment.Season;

import java.util.ArrayList;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class SerialInputData extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    private int duration;

    //private Double ratingMediu;

    public SerialInputData(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        //this.ratingMediu = 0.0;
    }

//    public Double getRatingMediu() {
//        return ratingMediu;
//    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    public void setDuration() {
        for (Season iterator : this.seasons) {
            this.duration += iterator.getDuration();
        }
    }

    public Double medieRating() {
        Double sum = 0.0;
        for (Season iterator : this.seasons) {
            sum += iterator.medieRating();
        }
        Double medie = 0.0;
        if (this.seasons.size() != 0) {
            medie = sum / this.seasons.size();
        }
        return medie;
    }

//    public void setRatingMediu() {
//        this.ratingMediu = this.medieRating();
//    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
