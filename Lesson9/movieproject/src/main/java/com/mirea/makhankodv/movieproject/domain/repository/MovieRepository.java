package com.mirea.makhankodv.movieproject.domain.repository;

import com.mirea.makhankodv.movieproject.domain.models.Movie;

public interface MovieRepository {
    boolean saveMovie(Movie movie);
    Movie getMovie();
} 