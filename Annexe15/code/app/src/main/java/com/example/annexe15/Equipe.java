package com.example.annexe15;

public class Equipe {
    private String nom;
    private String division;
    private Arena arena;


    public Equipe(String nom, String division, Arena arena) {
        this.nom = nom;
        this.division = division;
        this.arena = arena;
    }

    public String getNom() {
        return nom;
    }

    public String getDivision() {
        return division;
    }

    public Arena getArena() {
        return arena;
    }
}
