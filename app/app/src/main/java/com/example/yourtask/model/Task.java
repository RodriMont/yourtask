package com.example.yourtask.model;

import com.google.gson.annotations.SerializedName;

public class Task {

    @SerializedName("id")
    public int id;
    @SerializedName("nome_task")
    public String nome_task;
    @SerializedName("data_avvio")
    public String data_avvio;
    @SerializedName("data_scadenza")
    public String data_scadenza;
    @SerializedName("priorita")
    public int priorita;
    @SerializedName("id_progetto")
    public int id_progetto;

    public Task(int id, String nome_task, String data_avvio, String data_scadenza, int priorita, int id_progetto) {
        this.id = id;
        this.nome_task = nome_task;
        this.data_avvio = data_avvio;
        this.data_scadenza = data_scadenza;
        this.priorita = priorita;
        this.id_progetto = id_progetto;
    }
}