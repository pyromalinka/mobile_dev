package com.mirea.makhankodv.mireaproject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JokeService {
    @GET("api?format=json")
    Call<Joke> getJoke();  // Now it will return a Joke object
}
