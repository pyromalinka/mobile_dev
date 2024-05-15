package com.mirea.makhankodv.lesson6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.mirea.makhankodv.lesson6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public static final String SHARED_PREFS = "save_data";
    public static final String GROUP_NUMBER = "groupNumber";
    public static final String LIST_NUMBER = "listNumber";
    public static final String FAVORITE_MOVIE = "favoriteMovie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadPreferences();

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
            }
        });
    }

    public void savePreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(GROUP_NUMBER, binding.groupNumber.getText().toString());
        editor.putString(LIST_NUMBER, binding.listNumber.getText().toString());
        editor.putString(FAVORITE_MOVIE, binding.favoriteMovie.getText().toString());

        editor.apply();
    }

    public void loadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        String groupNumber = sharedPreferences.getString(GROUP_NUMBER, "");
        String listNumber = sharedPreferences.getString(LIST_NUMBER, "");
        String favoriteMovie = sharedPreferences.getString(FAVORITE_MOVIE, "");

        binding.groupNumber.setText(groupNumber);
        binding.listNumber.setText(listNumber);
        binding.favoriteMovie.setText(favoriteMovie);
    }
}
