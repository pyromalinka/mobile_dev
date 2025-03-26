package com.mirea.makhankodv.data.storage;

import com.mirea.makhankodv.data.storage.models.Movie;

public interface MovieStorage {
    Movie get();
    boolean save(Movie movie);
} 