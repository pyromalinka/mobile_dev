package com.mirea.makhankodv.movieproject.domain.usecases;

import com.mirea.makhankodv.movieproject.domain.models.Movie;
import com.mirea.makhankodv.movieproject.domain.repository.MovieRepository;

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