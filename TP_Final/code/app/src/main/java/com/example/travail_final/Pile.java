package com.example.travail_final;

import android.view.View;

import java.util.Objects;
import java.util.Vector;

public class Pile {

    private Vector<Carte> cartes;
    private int temp_points;
    private String type; // asc ou desc ou null

    public Pile(String type) {
        cartes = new Vector<>();
        this.type = type;
        if(type.toUpperCase() == "ASC"){
            cartes.add(new Carte(0));
        }else{
            cartes.add(new Carte(97));
        }
    }




    public Carte getDerniereCarte() {
        return this.cartes.lastElement();
    }

    public boolean isCarteValide(Carte carte) {
        if(Objects.equals(type, "ASC")){

            return this.cartes.lastElement().est_inferieure_a(carte);
        }
        else {
            return this.cartes.lastElement().est_superieur_a(carte);
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
