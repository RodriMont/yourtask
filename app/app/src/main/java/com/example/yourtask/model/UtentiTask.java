package com.example.yourtask.model;

import com.google.gson.annotations.SerializedName;

public class UtentiTask {

    @SerializedName("id_task")
    public int id_task;
    @SerializedName("id_utente")
    public int id_utente;

    public UtentiTask(int id_task, int id_utente) {
        this.id_task = id_task;
        this.id_utente = id_utente;
    }
}
