package com.example.yourtask.model;

import java.util.ArrayList;

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
        @GET("/utenti")
        Call<ArrayList<User>> getUsers(@Query("email")ArrayList<String> email);
        @GET("/progetti_utente")
        Call<ArrayList<Progetto>> getProgettiUtente(@Query("id")int id);
        @GET("/task_utente")
        Call<ArrayList<Task>> getTaskUtente(@Query("id_utente")int id_utente, @Query("id_progetto")int id_progetto);
        @GET("/utenti_task")
        Call<ArrayList<User>> getUtentiTask(@Query("id_task")int id_task, @Query("id_progetto")int id_progetto);
        @GET("/utenti_progetto")
        Call<ArrayList<User>> getUtentiProgetto(@Query("id_progetto")int id_progetto);
        @GET("/ruolo_utente")
        Call<ArrayList<Ruolo>> getRuoloUtente(@Query("id_utente")int id_utente, @Query("id_progetto")int id_progetto);

        @POST("/registrazione")
        Call<RequestResult> postUtente(@Body User user);
        @POST ("/progetti")
        Call<RequestResult> postProgetto(@Body Progetto progetto);
        @POST("/task")
        Call<RequestResult> postTask(@Body Task task);
        @POST("/ruoli")
        Call<RequestResult> postRuolo(@Body Ruolo ruolo);
        @POST ("/login")
        Call<User> postLogin(@Body User user);
        @POST("/utenti_progetto")
        Call<RequestResult> postUtentiProgetto(@Body ArrayList<UtentiProgetto> utentiProgetto);
        @POST("/utenti_task")
        Call<RequestResult> postUtentiTask(@Body ArrayList<UtentiTask> utentiTask);


        @DELETE("/utenti/{id}")
        Call<RequestResult> deleteUtente(@Path("id")int id);
        @DELETE("/progetti/{id}")
        Call<RequestResult> deleteProgetto(@Path("id")int id);
        @DELETE("/task/{id}")
        Call<RequestResult> deleteTask(@Path("id")int id);
        @DELETE("/ruoli/{id}")
        Call<RequestResult> deleteRuolo(@Path("id")int id);
        @DELETE("/utente_progetto/{id_utente}/{id_progetto}")
        Call<RequestResult> deleteUtenteProgetto(@Path("id_utente")int id_utente, @Path("id_progetto")int id_progetto);

        @PUT("/utenti/{id}")
        Call<RequestResult> putUtente(@Path("id")int id, @Body User user);
        @PUT("/progetti/{id}")
        Call<RequestResult> putProgetto(@Path("id")int id, @Body Progetto progetto);
        @PUT("/task/{id}")
        Call<RequestResult> putTask(@Path("id")int id, @Body Task task);
        @PUT("/ruoli/{id}")
        Call<RequestResult> putRuolo(@Path("id")int id, @Body Ruolo ruolo);
}
