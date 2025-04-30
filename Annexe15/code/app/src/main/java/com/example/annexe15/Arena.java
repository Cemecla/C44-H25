package com.example.annexe15;

public class Arena {
    private String nom;
    private int capacite;


    public Arena(String nom, int capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }

    public String getNom() {
        return nom;
    }

    public int getCapacite() {
        return capacite;
    }
}
