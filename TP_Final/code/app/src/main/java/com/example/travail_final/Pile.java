package com.example.travail_final;

import android.view.View;

import java.util.Objects;
import java.util.Vector;

public class Pile {

    private Vector<Carte> cartes;
    private int temp_points;
    private String type; // asc ou desc ou null

    public Pile(String type) {
        this.temp_points = 0;
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

    public boolean isCarteValide(Carte carte) {
        boolean diff10 = this.cartes.lastElement().est_different_de_10(carte);

        if(diff10){
            temp_points = 15;
        }else {temp_points = 5;}

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

    public int getPoints(){ // Renvoie les points que la dernière carte à aquise.
        return temp_points;
    }
    public void updatePile(){ // Valide le jeu en mettant les valeurs temporaire à par defaut.
        temp_points = 0;
    }
}
