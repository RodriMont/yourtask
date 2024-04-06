package com.example.yourtask.model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequest {
    private static String baseurl;
    private static Retrofit retrofit;
    private static ApiEndpoint apiService;

    public static void setup () {
        baseurl = "http://192.168.35.18:5000";
        retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiEndpoint.class);
    }

    //GET
    public static void getUtente (String email, ReceiveDataCallback <ArrayList<User>> callback)
    {
        Call <ArrayList<User>> call = apiService.getUser(email);
        call.enqueue(new Callback <ArrayList<User>>() {
            @Override
            public void onResponse(Call <ArrayList<User>> call, Response <ArrayList<User>> response) {
                callback.receiveData(response.body());
            }
            @Override
            public void onFailure(Call <ArrayList<User>> call, Throwable t) {
                callback.receiveData(null);
            }
        });
    }

    public static void getUtenti (ArrayList<String> email, ReceiveDataCallback <ArrayList<User>> callback)
    {
        Call <ArrayList<User>> call = apiService.getUsers(email);
        call.enqueue(new Callback <ArrayList<User>>() {
            @Override
            public void onResponse(Call <ArrayList<User>> call, Response <ArrayList<User>> response) {
                callback.receiveData(response.body());
            }
            @Override
            public void onFailure(Call <ArrayList<User>> call, Throwable t) {
                callback.receiveData(null);
            }
        });
    }

    public static void getProgettiUtente (int id, ReceiveDataCallback<ArrayList<Progetto>> callback) {

        Call<ArrayList<Progetto>> progetto = apiService.getProgettiUtente(id);
        progetto.enqueue(new Callback<ArrayList<Progetto>>() {
            @Override
            public void onResponse(Call<ArrayList<Progetto>> call, Response<ArrayList<Progetto>> response) {
                callback.receiveData(response.body());
            }
            @Override
            public void onFailure(Call<ArrayList<Progetto>> call, Throwable t) {
                callback.receiveData(null);
            }
        });
    }

    public static void getTaskUtente (int id_utente, int id_progetto, ReceiveDataCallback<ArrayList<Task>> callback) {

        Call<ArrayList<Task>> task = apiService.getTaskUtente(id_utente, id_progetto);
        task.enqueue(new Callback<ArrayList<Task>>() {
            @Override
            public void onResponse(Call<ArrayList<Task>> call, Response<ArrayList<Task>> response) {
                callback.receiveData(response.body());
            }
            @Override
            public void onFailure(Call<ArrayList<Task>> call, Throwable t) {
                callback.receiveData(null);
            }
        });
    }

    public static void getRuoloUtente (int id_utente, int id_progetto, ReceiveDataCallback<ArrayList<Ruolo>> callback) {

        Call<ArrayList<Ruolo>> ruoli = apiService.getRuoloUtente(id_utente, id_progetto);
        ruoli.enqueue(new Callback<ArrayList<Ruolo>>() {
            @Override
            public void onResponse(Call<ArrayList<Ruolo>> call, Response<ArrayList<Ruolo>> response) {
                callback.receiveData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Ruolo>> call, Throwable t) {
                callback.receiveData(null);
            }
        });
    }


    public static void getUtentiTask (int id_task, int id_progetto, ReceiveDataCallback<ArrayList<User>> callback) {

        Call<ArrayList<User>> utenti = apiService.getUtentiTask(id_task, id_progetto);
        utenti.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                callback.receiveData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                callback.receiveData(null);
            }
        });
    }

    public static void getUtentiProgetto (int id_progetto, ReceiveDataCallback<ArrayList<User>> callback) {

        Call<ArrayList<User>> utenti = apiService.getUtentiProgetto(id_progetto);
        utenti.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                callback.receiveData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                callback.receiveData(null);
            }
        });
    }

    //POST
    public static void postUtente (User user, ReceiveDataCallback<RequestResult> callback) {

        Call<RequestResult> call = apiService.postUtente(user);
        call.enqueue(new Callback<RequestResult>() {
            @Override
            public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
                callback.receiveData(response.body());
            }

            @Override
            public void onFailure(Call<RequestResult> call, Throwable t) {
                callback.receiveData(new RequestResult(500, -1, "Internal server error"));
            }
        });
    }

    public static void postLogin (User user, ReceiveDataCallback<Integer> callback) {

        Call<User> utenteLogin = apiService.postLogin(user);
        utenteLogin.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();
                callback.receiveData(statusCode);

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.receiveData(500);
            }
        });
    }

    public static void postProgetto (Progetto progetto, ReceiveDataCallback <RequestResult> callback) {

        Call<RequestResult> postProgetto = apiService.postProgetto(progetto);
        postProgetto.enqueue(new Callback<RequestResult>() {
            @Override
            public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
                callback.receiveData(response.body());
            }

            @Override
            public void onFailure(Call<RequestResult> call, Throwable t) {

            }
        });
    }

    public static void postTask (Task task, ReceiveDataCallback <RequestResult> callback) {

        Call<RequestResult> call = apiService.postTask(task);
        call.enqueue(new Callback<RequestResult>() {
            @Override
            public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
                callback.receiveData(response.body());
            }

            @Override
            public void onFailure(Call<RequestResult> call, Throwable t) {
                callback.receiveData(new RequestResult(500, -1, "Internal server error"));
            }
        });
    }

    public static void postRuolo (Ruolo ruolo, ReceiveDataCallback<RequestResult> callback) {

        Call<RequestResult> call = apiService.postRuolo(ruolo);
        call.enqueue(new Callback<RequestResult>() {
            @Override
            public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
                callback.receiveData(response.body());
            }

            @Override
            public void onFailure(Call<RequestResult> call, Throwable t) {
                callback.receiveData(new RequestResult(500, -1, "Internal server error"));
            }
        });
    }

    public static void postUtentiProgetto (ArrayList<UtentiProgetto> utentiProgetto, ReceiveDataCallback<Integer> callback) {

        Call<User> postUtentiProgetto = apiService.postUtentiProgetto(utentiProgetto);
        postUtentiProgetto.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();
                callback.receiveData(statusCode);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public static void postUtentiTask (UtentiTask utentiTask) {

        Call<User> postUtentiTask = apiService.postUtentiTask(utentiTask);
        postUtentiTask.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    //DELETE
    public static void deleteUtente (int id) {
        Call<User> canc = apiService.deleteUtente(id);
        canc.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public static void deleteProgetto (int id, ReceiveDataCallback <Integer> callback) {
        Call<Progetto> cancProgetto = apiService.deleteProgetto(id);
        cancProgetto.enqueue(new Callback<Progetto>() {
            @Override
            public void onResponse(Call<Progetto> call, Response<Progetto> response) {
                int statusCode = response.code();
                callback.receiveData(statusCode);
            }

            @Override
            public void onFailure(Call<Progetto> call, Throwable t) {

            }
        });
    }

    public static void deleteTask (int id, ReceiveDataCallback<Integer> callback) {
        Call<Task> cancTask = apiService.deleteTask(id);
        cancTask.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                int statusCode = response.code();
                callback.receiveData(statusCode);
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                callback.receiveData(500);
            }
        });
    }

    public static void deleteRuolo (int id) {
        Call<Ruolo> cancRuolo = apiService.deleteRuolo(id);
        cancRuolo.enqueue(new Callback<Ruolo>() {
            @Override
            public void onResponse(Call<Ruolo> call, Response<Ruolo> response) {
                int statusCode = response.code();

            }

            @Override
            public void onFailure(Call<Ruolo> call, Throwable t) {

            }
        });
    }

    //PUT
    public static void putUtente (int id, User user, ReceiveDataCallback <Integer> callback) {
        Call<User> putUser = apiService.putUtente(id, user);
        putUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();
                callback.receiveData(statusCode);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public static void putProgetto (int id, Progetto progetto, ReceiveDataCallback<Integer> callback) {
        Call<Progetto> putProgetto = apiService.putProgetto(id, progetto);
        putProgetto.enqueue(new Callback<Progetto>() {
            @Override
            public void onResponse(Call<Progetto> call, Response<Progetto> response) {
                int statusCode = response.code();
                callback.receiveData(statusCode);
            }

            @Override
            public void onFailure(Call<Progetto> call, Throwable t) {

            }
        });
    }

    public static void putTask (int id, Task task, ReceiveDataCallback<RequestResult> callback) {
        Call<RequestResult> putTask = apiService.putTask(id, task);
        putTask.enqueue(new Callback<RequestResult>() {
            @Override
            public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
                callback.receiveData(response.body());
            }

            @Override
            public void onFailure(Call<RequestResult> call, Throwable t) {

            }
        });
    }

    public static void putRuolo (int id, Ruolo ruolo) {
        Call<Ruolo> putRuolo = apiService.putRuolo(id, ruolo);
        putRuolo.enqueue(new Callback<Ruolo>() {
            @Override
            public void onResponse(Call<Ruolo> call, Response<Ruolo> response) {
                int statusCode = response.code();

            }

            @Override
            public void onFailure(Call<Ruolo> call, Throwable t) {

            }
        });
    }
}


