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

public class QuoteFragment extends Fragment {

    private TextView quoteTextView1, quoteTextView2, quoteTextView3;
    private Button refreshButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quote, container, false);
        quoteTextView1 = view.findViewById(R.id.quoteTextView1);
        quoteTextView2 = view.findViewById(R.id.quoteTextView2);
        quoteTextView3 = view.findViewById(R.id.quoteTextView3);
        refreshButton = view.findViewById(R.id.refreshButton);

        refreshButton.setOnClickListener(v -> fetchQuotes());

        fetchQuotes();
        return view;
    }

    private void fetchQuotes() {
        TextView[] quoteViews = {quoteTextView1, quoteTextView2, quoteTextView3};
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.quotable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuoteService quoteService = retrofit.create(QuoteService.class);
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            quoteService.getQuote().enqueue(new Callback<Quote>() {
                @Override
                public void onResponse(Call<Quote> call, Response<Quote> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        quoteViews[finalI].setText(response.body().getContent());
                    } else {
                        quoteViews[finalI].setText("Failed to retrieve quote");
                    }
                }

                @Override
                public void onFailure(Call<Quote> call, Throwable t) {
                    quoteViews[finalI].setText("Error: " + t.getMessage());
                }
            });
        }
    }
}