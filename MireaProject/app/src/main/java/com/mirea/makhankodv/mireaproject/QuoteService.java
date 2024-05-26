package com.mirea.makhankodv.mireaproject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuoteService {
    @GET("random")
    Call<Quote> getQuote();
}