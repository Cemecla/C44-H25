package com.example.travail_final;

import java.util.Objects;

public class Pile {
    private Carte carte;
    private String type; // asc ou desc ou null

    public Pile(Carte carte, String type) {
        this.carte = carte;
        this.type = type;
    }

    public Carte getCarte() {
        return carte;
    }

    public String getType() {
        return type;
    }

    public boolean setCarte(Carte carte) {
        if(Objects.equals(type, "asc")){
            return this.carte.est_inferieure_a(carte);
        }
        else {
            return this.carte.est_superieur_a(carte);
        }

    }
}
