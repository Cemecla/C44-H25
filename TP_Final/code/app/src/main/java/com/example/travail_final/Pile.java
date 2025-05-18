package com.example.travail_final;

import android.view.View;

import java.util.Objects;
import java.util.Vector;

public class Pile {

    private Vector<Carte> cartes;

    private String type; // asc ou desc ou null

    public Pile(String type) {

        cartes = new Vector<>();
        this.type = type;
        if(type.equalsIgnoreCase("ASC")){
            cartes.add(new Carte(0));
        }else{
            cartes.add(new Carte(98));
        }
    }




    public Carte getDerniereCarte() {
        return this.cartes.lastElement();
    }

    public Carte retirer_carte(){
        return this.cartes.remove(this.cartes.size()-1);
    }

    public boolean isCarteValide(Carte carte) {
        boolean diff10 = this.cartes.lastElement().est_different_de_10(carte);


        if(Objects.equals(type, "ASC")){

                return this.cartes.lastElement().est_inferieure_a(carte) || diff10;
        }
        else {
                return this.cartes.lastElement().est_superieur_a(carte) || diff10;

        }
    }



    public void add_carte(Carte carte) { // Ajout d'une carte sur le paquet.
        this.cartes.add(carte);
    }

    public int get_valeur(){
        int score_par_carte = cartes.size();
        int score_par_somme = cartes.stream().mapToInt(Carte::getNumero).sum(); // rappel du cours de session passée
        int score_par_diff10 = 0;

        for (int i = 0; i < cartes.size()-1; i++) {
            if(cartes.get(i).est_different_de_10(cartes.get(i+1))){
                score_par_diff10+=10;
            }
            else
                score_par_diff10+=2;

        }

        return score_par_diff10 + score_par_somme + score_par_carte;
    }

}
