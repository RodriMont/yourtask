package com.example.yourtask.model;

import com.google.gson.annotations.SerializedName;

public class UtentiProgetto {

    @SerializedName("id_utente")
    public int id_utente;
    @SerializedName("id_progetto")
    public int id_progetto;

    public UtentiProgetto(int id_utente, int id_progetto) {
        this.id_utente = id_utente;
        this.id_progetto = id_progetto;
    }
}
