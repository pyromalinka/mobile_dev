package com.mirea.makhankodv.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JokeFragment extends Fragment {

    private TextView jokeTextView1, jokeTextView2, jokeTextView3;
    private Button refreshButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joke, container, false);
        jokeTextView1 = view.findViewById(R.id.jokeTextView1);
        jokeTextView2 = view.findViewById(R.id.jokeTextView2);
        jokeTextView3 = view.findViewById(R.id.jokeTextView3);
        refreshButton = view.findViewById(R.id.refreshButton);

        refreshButton.setOnClickListener(v -> fetchJokes());

        fetchJokes();  // Initial jokes fetch
        return view;
    }

    private void fetchJokes() {
        TextView[] jokeViews = {jokeTextView1, jokeTextView2, jokeTextView3};
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://geek-jokes.sameerkumar.website/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JokeService jokeService = retrofit.create(JokeService.class);
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            jokeService.getJoke().enqueue(new Callback<Joke>() {
                @Override
                public void onResponse(Call<Joke> call, Response<Joke> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        jokeViews[finalI].setText(response.body().getJoke());
                    } else {
                        jokeViews[finalI].setText("Failed to retrieve joke");
                    }
                }

                @Override
                public void onFailure(Call<Joke> call, Throwable t) {
                    jokeViews[finalI].setText("Error: " + t.getMessage());
                }
            });
        }
    }

    private void updateJokeText(Joke joke, String url) {
        if (url.contains("joke1")) jokeTextView1.setText(joke.getJoke());
        else if (url.contains("joke2")) jokeTextView2.setText(joke.getJoke());
        else if (url.contains("joke3")) jokeTextView3.setText(joke.getJoke());
        else jokeTextView1.setText("Failed to load joke");
    }
}
