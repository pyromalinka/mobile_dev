package com.mirea.makhankodv.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private EditText etName, etAge, etFavoriteMovie, etFavoriteBook, etFavoriteVideoGame;
    private Button btnSave;

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "user_profile";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        etName = view.findViewById(R.id.etName);
        etAge = view.findViewById(R.id.etAge);
        etFavoriteMovie = view.findViewById(R.id.etFavoriteMovie);
        etFavoriteBook = view.findViewById(R.id.etFavoriteBook);
        etFavoriteVideoGame = view.findViewById(R.id.etFavoriteVideoGame);
        btnSave = view.findViewById(R.id.btnSave);

        sharedPreferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        loadProfile();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        return view;
    }

    private void saveProfile() {
        String name = etName.getText().toString();
        String age = etAge.getText().toString();
        String favoriteMovie = etFavoriteMovie.getText().toString();
        String favoriteBook = etFavoriteBook.getText().toString();
        String favoriteVideoGame = etFavoriteVideoGame.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("age", age);
        editor.putString("favorite_movie", favoriteMovie);
        editor.putString("favorite_book", favoriteBook);
        editor.putString("favorite_video_game", favoriteVideoGame);
        editor.apply();
    }

    private void loadProfile() {
        String name = sharedPreferences.getString("name", "");
        String age = sharedPreferences.getString("age", "");
        String favoriteMovie = sharedPreferences.getString("favorite_movie", "");
        String favoriteBook = sharedPreferences.getString("favorite_book", "");
        String favoriteVideoGame = sharedPreferences.getString("favorite_video_game", "");

        etName.setText(name);
        etAge.setText(age);
        etFavoriteMovie.setText(favoriteMovie);
        etFavoriteBook.setText(favoriteBook);
        etFavoriteVideoGame.setText(favoriteVideoGame);
    }
}