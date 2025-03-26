package com.mirea.makhankodv.domain.usecases;

import com.mirea.makhankodv.domain.models.Movie;
import com.mirea.makhankodv.domain.repository.MovieRepository;

public class GetFavoriteFilmUseCase {
    private MovieRepository movieRepository;

    public GetFavoriteFilmUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie execute() {
        return movieRepository.getMovie();
    }
} 