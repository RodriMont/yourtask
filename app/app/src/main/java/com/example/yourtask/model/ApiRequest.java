package com.example.yourtask.model;

import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequest {

    private static ArrayList<User> user;
    public static ArrayList<Progetto> progettiUtente;
    public static ArrayList<Task> taskUtente;
    public static ArrayList<User> utentiTask;
    public static ArrayList<User> utentiProgetto;
    public static ArrayList<Ruolo> ruoloUtente;
    private static String baseurl;
    public static Retrofit retrofit;
    public static ApiEndpoint apiService;
    public static void setup () {
        baseurl = "http://192.168.0.112:5000";
        retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiEndpoint.class);
    }

    //GET
    public static ArrayList<User> getUtente (String email, ReceiveDataCallback <ArrayList<User>> callback)
    {

        Call <ArrayList<User>> call = apiService.getUser(email);
        call.enqueue(new Callback <ArrayList<User>>() {
            @Override
            public void onResponse(Call <ArrayList<User>> call, Response <ArrayList<User>> response) {
                ApiRequest.user = response.body();

                if (callback != null)
                    callback.receiveData(ApiRequest.user);
            }
            @Override
            public void onFailure(Call <ArrayList<User>> call, Throwable t) {

            }
        });

        return user;
    }

    public static ArrayList<Progetto> getProgettiUtente (int id, ReceiveDataCallback<ArrayList<Progetto>> callback) {

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
    public static ArrayList<Ruolo> getRuoloUtente (int id_utente, int id_progetto, ReceiveDataCallback<ArrayList<Ruolo>> callback) {

        Call<ArrayList<Ruolo>> ruoli = apiService.getRuoloUtente(id_utente, id_progetto);
        ruoli.enqueue(new Callback<ArrayList<Ruolo>>() {
            @Override
            public void onResponse(Call<ArrayList<Ruolo>> call, Response<ArrayList<Ruolo>> response) {
                int statusCode = response.code();
                ApiRequest.ruoloUtente = response.body();

                if (callback != null)
                    callback.receiveData(ApiRequest.ruoloUtente);
            }

            @Override
            public void onFailure(Call<ArrayList<Ruolo>> call, Throwable t) {

            }
        });

        return ruoloUtente;
    }


    public static ArrayList<User> getUtentiTask (int id_task, int id_progetto, ReceiveDataCallback<ArrayList<User>> callback) {

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

    public static ArrayList<User> getUtentiProgetto (int id_progetto, ReceiveDataCallback<ArrayList<User>> callback) {

        Call<ArrayList<User>> utenti = apiService.getUtentiProgetto(id_progetto);
        utenti.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                int statusCode = response.code();
                ApiRequest.utentiProgetto = response.body();

                if (callback != null)
                    callback.receiveData(ApiRequest.utentiProgetto);
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });

        return utentiProgetto;
    }

    //POST
    public static void postUtente (User user, ReceiveDataCallback<Integer> callback) {

        Call<User> nuovoUtente = apiService.postUtente(user);
        nuovoUtente.enqueue(new Callback<User>() {
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

    public static void postProgetto (Progetto progetto, ReceiveDataCallback <Integer> callback) {

        Call<Progetto> postProgetto = apiService.postProgetto(progetto);
        postProgetto.enqueue(new Callback<Progetto>() {
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

    public static void postTask (Task task, ReceiveDataCallback <Integer> callback) {

        Call<Task> postTask = apiService.postTask(task);
        postTask.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                int statusCode = response.code();
                callback.receiveData(statusCode);
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {

            }
        });
    }

    public static void postRuolo (Ruolo ruolo) {

        Call<Ruolo> postRuolo = apiService.postRuolo(ruolo);
        postRuolo.enqueue(new Callback<Ruolo>() {
            @Override
            public void onResponse(Call<Ruolo> call, Response<Ruolo> response) {
                int statusCode = response.code();

            }

            @Override
            public void onFailure(Call<Ruolo> call, Throwable t) {

            }
        });
    }

    public static void postUtentiProgetto (UtentiProgetto utentiProgetto) {

        Call<User> postUtentiProgetto = apiService.postUtentiProgetto(utentiProgetto);
        postUtentiProgetto.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();

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

    public static void deleteTask (int id) {
        Call<Task> cancTask = apiService.deleteTask(id);
        cancTask.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                int statusCode = response.code();

            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {

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

    public static void putTask (int id, Task task) {
        Call<Task> putTask = apiService.putTask(id, task);
        putTask.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                int statusCode = response.code();

            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {

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


