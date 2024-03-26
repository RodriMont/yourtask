package com.example.yourtask.model;

import com.google.gson.annotations.SerializedName;

public class Progetto {
    @SerializedName("id")
    public int id;
    @SerializedName("nome_progetto")
    public String nome_progetto;
    @SerializedName("data_avvio")
    public String data_avvio;
    @SerializedName("data_scadenza")
    public String data_scadenza;
    @SerializedName("budget")
    public float budget;

    public Progetto (int id, String nome_progetto, String data_avvio, String data_scadenza, float budget ) {
        this.id = id;
        this.nome_progetto = nome_progetto;
        this.data_avvio = data_avvio;
        this.data_scadenza = data_scadenza;
        this.budget = budget;
    }

}
