package com.example.yourtask.model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpoint {
        @GET("/utente")
        Call<ArrayList<User>> getUser(@Query("email")String email);
        @GET("/progetti_utente")
        Call<ArrayList<Progetto>> getProgettiUtente(@Query("id")int id);
        @GET("/task_utente")
        Call<ArrayList<Task>> getTaskUtente(@Query("id_utente")int id_utente, @Query("id_progetto")int id_progetto);
        @GET("/utenti_task")
        Call<ArrayList<User>> getUtentiTask(@Query("id_task")int id_task, @Query("id_progetto")int id_progetto);

        @POST("/registrazione")
        Call<User> postUtente(@Body User user);
        @POST ("/progetti")
        Call<Progetto> postProgetto(@Body Progetto progetto);
        @POST("/task")
        Call<Task> postTask(@Body Task task);
        @POST("/ruoli")
        Call<Ruolo> postRuolo(@Body Ruolo ruolo);

        @DELETE("/utenti/{id}")
        Call<User> deleteUtente(@Path("id")int id);
        @DELETE("/progetti/{id}")
        Call<Progetto> deleteProgetto(@Path("id")int id);
        @DELETE("/task/{id}")
        Call<Task> deleteTask(@Path("id")int id);

        @PUT("/utenti/{id}")
        Call<User> putUtente(@Path("id")int id, @Body User user);
        @PUT("/progetti/{id}")
        Call<Progetto> putProgetto(@Path("id")int id, @Body Progetto progetto);
        @PUT("/task/{id}")
        Call<Task> putTask(@Path("id")int id, @Body Task task);
}
