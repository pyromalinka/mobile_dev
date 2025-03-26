package com.mirea.makhankodv.movieproject.presentation;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.mirea.makhankodv.data.repository.MovieRepositoryImpl;
import com.mirea.makhankodv.data.storage.sharedprefs.SharedPrefMovieStorage;
import com.mirea.makhankodv.domain.models.Movie;
import com.mirea.makhankodv.movieproject.R;

public class MainActivity extends AppCompatActivity {
    private TextView textViewMovie;
    private MovieRepositoryImpl movieRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewMovie = findViewById(R.id.textViewMovie);
        SharedPrefMovieStorage movieStorage = new SharedPrefMovieStorage(this);
        movieRepository = new MovieRepositoryImpl(movieStorage);

        // Пример сохранения фильма
        Movie movie = new Movie(1, "The Matrix");
        movieRepository.saveMovie(movie);

        // Получение и отображение фильма
        Movie savedMovie = movieRepository.getMovie();
        textViewMovie.setText(savedMovie.getName());
    }
} 