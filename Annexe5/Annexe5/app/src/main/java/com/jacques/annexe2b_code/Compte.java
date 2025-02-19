package com.jacques.annexe2b_code;

public class Compte {
    private String nom;
    private double solde;

    public Compte(String nom, double solde) {
        this.nom = nom;
        this.solde = solde;
    }

    public String getNom() {
        return nom;
    }

    public double getSolde() {
        return solde;
    }

    public boolean transfer(double montant) {

        if(this.solde >= montant ){
            this.solde -= montant;
            return true;
        }
        else
            return false;
    }

}
