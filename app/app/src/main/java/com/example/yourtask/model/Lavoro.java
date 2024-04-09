package com.example.yourtask.model;

import com.google.gson.annotations.SerializedName;

public final class Lavoro
{
    @SerializedName("id_utente")
    public int id_utente;
    @SerializedName("id_progetto")
    public int id_progetto;
    @SerializedName("id_task")
    public int id_task;
    @SerializedName("id_ruolo")
    public int id_ruolo;

    public Lavoro(int id_utente, int id_progetto, int id_task, int id_ruolo)
    {
        this.id_utente = id_utente;
        this.id_progetto = id_progetto;
        this.id_task = id_task;
        this.id_ruolo = id_ruolo;
    }
}
