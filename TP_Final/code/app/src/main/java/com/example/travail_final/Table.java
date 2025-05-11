package com.example.travail_final;

import android.widget.LinearLayout;

import java.util.Vector;

public class Table {
    private Vector<Carte> cartes;

    public Table() {
        this.cartes = new Vector<>(8);
    }

    public int get_nbr_cartes(){
        return this.cartes.size();
    }

    public void ajouter_cartes(){

    }
}
