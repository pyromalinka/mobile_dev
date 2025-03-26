package com.mirea.makhankodv.data.repository;

import com.mirea.makhankodv.data.storage.MovieStorage;
import com.mirea.makhankodv.domain.repository.MovieRepository;
import com.mirea.makhankodv.domain.models.Movie;

public class MovieRepositoryImpl implements MovieRepository {
    private final MovieStorage movieStorage;

    public MovieRepositoryImpl(MovieStorage movieStorage) {
        this.movieStorage = movieStorage;
    }

    @Override
    public boolean saveMovie(Movie movie) {
        return movieStorage.save(mapToStorage(movie));
    }

    @Override
    public Movie getMovie() {
        com.mirea.makhankodv.data.storage.models.Movie storageMovie = movieStorage.get();
        return mapToDomain(storageMovie);
    }

    private com.mirea.makhankodv.data.storage.models.Movie mapToStorage(Movie movie) {
        return new com.mirea.makhankodv.data.storage.models.Movie(2, movie.getName(), java.time.LocalDate.now().toString());
    }

    private Movie mapToDomain(com.mirea.makhankodv.data.storage.models.Movie movie) {
        return new Movie(movie.getId(), movie.getName());
    }
} 