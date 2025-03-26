package com.mirea.makhankodv.movieproject.data.repository;

import com.mirea.makhankodv.movieproject.data.storage.LocalStorage;
import com.mirea.makhankodv.movieproject.domain.models.Movie;
import com.mirea.makhankodv.movieproject.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {
    private static final String KEY_MOVIE_ID = "movie_id";
    private static final String KEY_MOVIE_NAME = "movie_name";
    
    private final LocalStorage localStorage;

    public MovieRepositoryImpl(LocalStorage localStorage) {
        this.localStorage = localStorage;
    }

    @Override
    public boolean saveMovie(Movie movie) {
        try {
            localStorage.saveInt(KEY_MOVIE_ID, movie.getId());
            localStorage.saveString(KEY_MOVIE_NAME, movie.getName());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Movie getMovie() {
        int id = localStorage.getInt(KEY_MOVIE_ID, -1);
        String name = localStorage.getString(KEY_MOVIE_NAME, "");
        return new Movie(id, name);
    }
} 