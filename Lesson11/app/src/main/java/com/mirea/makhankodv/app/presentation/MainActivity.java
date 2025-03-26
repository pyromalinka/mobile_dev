package com.mirea.makhankodv.app.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mirea.makhankodv.app.R;
import com.mirea.makhankodv.domain.models.Movie;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private EditText movieEditText;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(MainActivity.class.getSimpleName(), "MainActivity created");

        mainViewModel = new ViewModelProvider(this, new ViewModelFactory(this))
                .get(MainViewModel.class);

        movieEditText = findViewById(R.id.editTextMovie);
        resultTextView = findViewById(R.id.textViewMovie);

        mainViewModel.getFavoriteMovie().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String text) {
                resultTextView.setText(text);
            }
        });

        findViewById(R.id.buttonSaveMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieName = movieEditText.getText().toString();
                mainViewModel.setText(new Movie(1, movieName));
            }
        });

        findViewById(R.id.buttonGetMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.getText();
            }
        });
    }
} 