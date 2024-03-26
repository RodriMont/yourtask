package com.example.yourtask.model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpoint {
        @GET("/utente")
        Call<User> getUser(@Query("email")String email);
        @GET("/progetti_utente")
        Call<ArrayList<Progetto>> getProgettiUtente(@Query("id")int id);
        @GET("/task_utente")
        Call<ArrayList<Task>> getTaskUtente(@Query("id_utente")int id_utente, @Query("id_progetto")int id_progetto);
        @GET("/utenti_task")
        Call<ArrayList<User>> getUtentiTask(@Query("id_task")int id_task, @Query("id_progetto")int id_progetto);

        @POST("/registrazione")
        Call<User> postUtente(@Body User user);
}
