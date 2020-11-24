package myclasses;

import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;

public final class ParsingInputLoader {

    private Input input;

    public ParsingInputLoader(final Input input) {
        this.input = input;
    }

    public Input getInput() {
        return input;
    }
    /**
     * parsing all the input from the object input of class Input into my own class
     * ParsingInput
     */
    public ParsingInput parseAll() {
        List<Actor> actors = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();
        List<User> users = new ArrayList<>();
        //coppying all the actors
        if (this.input.getActors() != null) {
            for (ActorInputData iterator : this.input.getActors()) {
                actors.add(new Actor(iterator.getName(), iterator.getCareerDescription(),
                        iterator.getFilmography(), iterator.getAwards()));
            }
        }
        if (this.input.getMovies() != null) {
            for (MovieInputData iterator : this.input.getMovies()) {
                movies.add(new Movie(iterator.getTitle(), iterator.getCast(), iterator.getGenres(),
                        iterator.getYear(), iterator.getDuration()));
            }
        }

        if (this.input.getUsers() != null) {
            for (UserInputData iterator : this.input.getUsers()) {
                users.add(new User(iterator.getUsername(), iterator.getSubscriptionType(),
                        iterator.getHistory(), iterator.getFavoriteMovies()));
            }
        }
        return new ParsingInput(actors, users, this.input.getCommands(), movies,
                this.input.getSerials());
    }
}
