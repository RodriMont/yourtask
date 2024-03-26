package com.example.yourtask.model;

import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequest {
    private static User user;
    public static ArrayList<Progetto> progettiUtente;
    public static ArrayList<Task> taskUtente;
    public static ArrayList<User> utentiTask;

    public static User getUtente (String email, ReceiveDataCallback<User> callback)
    {

        String BASE_URL = "http://192.168.0.111:5000";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpoint apiService = retrofit.create(ApiEndpoint.class);

        Call<User> call = apiService.getUser(email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                ApiRequest.user = response.body();

                if (callback != null)
                    callback.receiveData(ApiRequest.user);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        return user;
    }

    public static ArrayList<Progetto> getProgettiUtente (int id, ReceiveDataCallback<ArrayList<Progetto>> callback) {

        String BASE_URL = "http://192.168.0.111:5000";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpoint apiService = retrofit.create(ApiEndpoint.class);

        Call<ArrayList<Progetto>> progetto = apiService.getProgettiUtente(id);
        progetto.enqueue(new Callback<ArrayList<Progetto>>() {
            @Override
            public void onResponse(Call<ArrayList<Progetto>> call, Response<ArrayList<Progetto>> response) {
                int statusCode = response.code();
                ApiRequest.progettiUtente = response.body();

                if(callback != null)
                    callback.receiveData(ApiRequest.progettiUtente);
            }
            @Override
            public void onFailure(Call<ArrayList<Progetto>> call, Throwable t) {

            }
        });

        return progettiUtente;
    }

    public static ArrayList<Task> getTaskUtente (int id_utente, int id_progetto, ReceiveDataCallback<ArrayList<Task>> callback) {

        String BASE_URL = "http://192.168.0.111:5000";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpoint apiService = retrofit.create(ApiEndpoint.class);

        Call<ArrayList<Task>> task = apiService.getTaskUtente(id_utente, id_progetto);
        task.enqueue(new Callback<ArrayList<Task>>() {
            @Override
            public void onResponse(Call<ArrayList<Task>> call, Response<ArrayList<Task>> response) {
                int statusCode = response.code();
                ApiRequest.taskUtente = response.body();

                if(callback != null)
                    callback.receiveData(ApiRequest.taskUtente);
            }
            @Override
            public void onFailure(Call<ArrayList<Task>> call, Throwable t) {

            }
        });

        return taskUtente;

    }
    public static ArrayList<User> getUtentiTask (int id_task, int id_progetto, ReceiveDataCallback<ArrayList<User>> callback) {

        String BASE_URL = "http://192.168.0.111:5000";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpoint apiService = retrofit.create(ApiEndpoint.class);

        Call<ArrayList<User>> utenti = apiService.getUtentiTask(id_task, id_progetto);
        utenti.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                int statusCode = response.code();
                ApiRequest.utentiTask = response.body();

                if (callback != null)
                    callback.receiveData(ApiRequest.utentiTask);
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });

        return utentiTask;
    }

    public static void postUtente (User username) {

        String BASE_URL = "http://192.168.0.111:5000";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpoint apiService = retrofit.create(ApiEndpoint.class);

        Call<User> nuovoUtente = apiService.postUtente(username);
        nuovoUtente.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();
                ApiRequest.user = response.body();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
