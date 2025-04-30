package com.example.annexe13;

public class Evaluation {
    private String nom;
    private String micro;
    private float eval;

    public Evaluation(String nom, String micro, float eval) {
        this.nom = nom;
        this.micro = micro;
        this.eval = eval;
    }

    public String getNom() {
        return nom;
    }

    public String getMicro() {
        return micro;
    }

    public float getEval() {
        return eval;
    }
}
