package com.mirea.makhankodv.movieproject.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesStorage implements LocalStorage {
    private static final String PREFS_NAME = "MoviePrefs";
    private final SharedPreferences preferences;

    public SharedPreferencesStorage(Context context) {
        this.preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void saveInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    @Override
    public void saveString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }
} 