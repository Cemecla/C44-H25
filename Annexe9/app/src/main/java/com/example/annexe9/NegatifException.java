package com.example.annexe9;


//c'est une exception contrôlée
public class NegatifException extends Exception{

    private double montantErrone;


    public NegatifException (double montantErrone){

        super("Le montant: " + montantErrone + ", ne peux pas être négatif !");
        this.montantErrone = montantErrone;



    }

    public double getMontantErrone() {
        return montantErrone;
    }
}
