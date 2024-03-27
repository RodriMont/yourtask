package com.example.yourtask.model;

public class Project {
    public int id;
    public String nomeProgetto;
    public String dataAvvio;
    public String dataScadenza;
    public double budget;

    public Project(int id, String nomeProgetto, String dataAvvio, String dataScadenza, double budget) {
        this.id = id;
        this.nomeProgetto = nomeProgetto;
        this.dataAvvio = dataAvvio;
        this.dataScadenza = dataScadenza;
        this.budget = budget;
    }
}
