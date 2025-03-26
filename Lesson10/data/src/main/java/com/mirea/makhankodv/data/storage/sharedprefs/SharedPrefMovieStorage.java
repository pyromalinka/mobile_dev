package com.mirea.makhankodv.data.storage.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.annotation.SuppressLint;

import com.mirea.makhankodv.data.storage.MovieStorage;
import com.mirea.makhankodv.data.storage.models.Movie;

public class SharedPrefMovieStorage implements MovieStorage {
    private static final String PREF_NAME = "MoviePrefs";
    private static final String KEY_MOVIE_ID = "movie_id";
    private static final String KEY_MOVIE_NAME = "movie_name";
    private static final String KEY_MOVIE_DATE = "movie_date";

    private final SharedPreferences sharedPreferences;

    public SharedPrefMovieStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public Movie get() {
        int id = sharedPreferences.getInt(KEY_MOVIE_ID, 0);
        String name = sharedPreferences.getString(KEY_MOVIE_NAME, "");
        String date = sharedPreferences.getString(KEY_MOVIE_DATE, "");
        return new Movie(id, name, date);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public boolean save(Movie movie) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_MOVIE_ID, movie.getId());
        editor.putString(KEY_MOVIE_NAME, movie.getName());
        editor.putString(KEY_MOVIE_DATE, movie.getDate());
        return editor.commit();
    }
} 