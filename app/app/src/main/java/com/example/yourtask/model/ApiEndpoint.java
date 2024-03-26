package com.example.yourtask.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpoint {
        @GET("/utente")
        Call<User> getUser(@Query("email")String email);
        @POST("/registrazione")
        Call<User> createUser(@Body User user);
}
