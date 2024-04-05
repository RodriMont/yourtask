package com.example.yourtask.model;

import com.google.gson.annotations.SerializedName;

public class Ruolo {

    @SerializedName("id")
    public int id;
    @SerializedName("nome_ruolo")
    public String nome_ruolo;
    @SerializedName("colore")
    public String colore;
    @SerializedName("id_progetto")
    public int id_progetto;

    public Ruolo (int id, String nome_ruolo, String colore, int id_progetto) {
        this.id = id;
        this.nome_ruolo = nome_ruolo;
        this.colore = colore;
        this.id_progetto = id_progetto;
    }
}
