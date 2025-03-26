package com.mirea.makhankodv.domain.repository;

import com.mirea.makhankodv.domain.models.Movie;

public interface MovieRepository {
    boolean saveMovie(Movie movie);
    Movie getMovie();
} 