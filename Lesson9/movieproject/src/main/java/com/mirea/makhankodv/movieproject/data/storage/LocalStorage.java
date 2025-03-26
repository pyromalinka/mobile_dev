package com.mirea.makhankodv.movieproject.data.storage;

public interface LocalStorage {
    void saveInt(String key, int value);
    void saveString(String key, String value);
    int getInt(String key, int defaultValue);
    String getString(String key, String defaultValue);
} 