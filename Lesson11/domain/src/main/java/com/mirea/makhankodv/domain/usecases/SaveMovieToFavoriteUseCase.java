package com.mirea.makhankodv.domain.usecases;

import com.mirea.makhankodv.domain.models.Movie;
import com.mirea.makhankodv.domain.repository.MovieRepository;

public class SaveMovieToFavoriteUseCase {
    private MovieRepository movieRepository;

    public SaveMovieToFavoriteUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public boolean execute(Movie movie) {
        if (movie.getName().isEmpty()) {
            return false;
        }
        return movieRepository.saveMovie(movie);
    }
} 