package com.mirea.makhankodv.app.presentation;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.mirea.makhankodv.domain.models.Movie;
import com.mirea.makhankodv.domain.repository.MovieRepository;
import com.mirea.makhankodv.domain.usecases.GetFavoriteFilmUseCase;
import com.mirea.makhankodv.domain.usecases.SaveMovieToFavoriteUseCase;

public class MainViewModel extends ViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();
    private MovieRepository movieRepository;
    private MutableLiveData<String> favoriteMovie = new MutableLiveData<>();

    public MainViewModel(MovieRepository movieRepository) {
        Log.d(TAG, "MainViewModel created");
        this.movieRepository = movieRepository;
    }

    @Override
    protected void onCleared() {
        Log.d(TAG, "MainViewModel cleared");
        super.onCleared();
    }

    public MutableLiveData<String> getFavoriteMovie() {
        Log.d(TAG, "Getting favoriteMovie LiveData");
        return favoriteMovie;
    }

    public void setText(Movie movie) {
        Log.d(TAG, "Saving movie: " + movie.getName());
        boolean result = new SaveMovieToFavoriteUseCase(movieRepository).execute(movie);
        favoriteMovie.setValue(result ? "Сохранено успешно" : "Ошибка сохранения");
    }

    public void getText() {
        Log.d(TAG, "Getting saved movie");
        Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
        favoriteMovie.setValue(String.format("Любимый фильм: %s", movie.getName()));
    }
} 