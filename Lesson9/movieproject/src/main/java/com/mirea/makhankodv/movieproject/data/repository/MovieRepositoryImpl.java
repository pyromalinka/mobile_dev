package com.mirea.makhankodv.movieproject.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.mirea.makhankodv.movieproject.domain.models.Movie;
import com.mirea.makhankodv.movieproject.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {
    private static final String PREFS_NAME = "MoviePrefs";
    private static final String KEY_MOVIE_ID = "movie_id";
    private static final String KEY_MOVIE_NAME = "movie_name";
    
    private final SharedPreferences preferences;

    public MovieRepositoryImpl(Context context) {
        this.preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean saveMovie(Movie movie) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_MOVIE_ID, movie.getId());
        editor.putString(KEY_MOVIE_NAME, movie.getName());
        return editor.commit();
    }

    @Override
    public Movie getMovie() {
        int id = preferences.getInt(KEY_MOVIE_ID, -1);
        String name = preferences.getString(KEY_MOVIE_NAME, "");
        return new Movie(id, name);
    }
} 