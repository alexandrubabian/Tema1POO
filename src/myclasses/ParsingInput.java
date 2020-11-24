package myclasses;

import fileio.ActionInputData;
import fileio.SerialInputData;

import java.util.List;

public final class ParsingInput {

    private final List<Actor> actors;
    /**
     * List of users
     */
    private final List<User> users;
    /**
     * List of commands
     */
    private final List<ActionInputData> commands;
    /**
     * List of movies
     */
    private final List<Movie> movies;
    /**
     * List of serials aka tv shows
     */
    private final List<SerialInputData> serials;

    public ParsingInput(final List<Actor> actors, final List<User> users,
                        final List<ActionInputData> commands,
                        final List<Movie> movies,
                        final List<SerialInputData> serials) {
        this.actors = actors;
        this.movies = movies;
        this.serials = serials;
        this.commands = commands;
        this.users = users;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<ActionInputData> getCommands() {
        return commands;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<SerialInputData> getSerials() {
        return serials;
    }
}
