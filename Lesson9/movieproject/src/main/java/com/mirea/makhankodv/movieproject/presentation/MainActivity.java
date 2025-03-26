package com.mirea.makhankodv.movieproject.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mirea.makhankodv.movieproject.R;
import com.mirea.makhankodv.movieproject.data.repository.MovieRepositoryImpl;
import com.mirea.makhankodv.movieproject.data.storage.LocalStorage;
import com.mirea.makhankodv.movieproject.data.storage.SharedPreferencesStorage;
import com.mirea.makhankodv.movieproject.domain.models.Movie;
import com.mirea.makhankodv.movieproject.domain.repository.MovieRepository;
import com.mirea.makhankodv.movieproject.domain.usecases.GetFavoriteFilmUseCase;
import com.mirea.makhankodv.movieproject.domain.usecases.SaveMovieToFavoriteUseCase;

public class MainActivity extends AppCompatActivity {
    private MovieRepository movieRepository;
    private EditText movieEditText;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Сначала создаем SharedPreferencesStorage
        LocalStorage localStorage = new SharedPreferencesStorage(this);
        // Затем передаем его в MovieRepositoryImpl
        movieRepository = new MovieRepositoryImpl(localStorage);

        movieEditText = findViewById(R.id.editTextMovie);
        resultTextView = findViewById(R.id.textViewMovie);

        findViewById(R.id.buttonSaveMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieName = movieEditText.getText().toString();
                boolean result = new SaveMovieToFavoriteUseCase(movieRepository)
                        .execute(new Movie(1, movieName));
                resultTextView.setText(String.format("Результат сохранения: %s", result));
            }
        });

        findViewById(R.id.buttonGetMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
                resultTextView.setText(String.format("Любимый фильм: %s", movie.getName()));
            }
        });
    }
} 