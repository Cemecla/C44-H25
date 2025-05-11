package com.example.travail_final;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;


public class MainActivity extends AppCompatActivity {


    Vector<Carte> carte_restantes;
    Ecouteur ecouteur;

    TextView label_cartes;

    Button menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    //Méthode ON_CREATE
        ecouteur = new Ecouteur();
        remplir_cartes();
        label_cartes = findViewById(R.id.label_cartes);

        //Ecouteurs sur les cartes sur la table
        LinearLayout cards_from = findViewById(R.id.cards_from);
        for (int i = 0; i < cards_from.getChildCount(); i++) {
            //Pour chaque colonne
                LinearLayout colonne = (LinearLayout) cards_from.getChildAt(i);
            for (int j = 0; j < colonne.getChildCount(); j++) {
                //Pour chaque item par rangée
                LinearLayout item = (LinearLayout) colonne.getChildAt(j);
                ConstraintLayout conteneur_carte = (ConstraintLayout) item.getChildAt(0);
                //conteneur_carte.getChildAt(0).setOnTouchListener(ecouteur);
                conteneur_carte.setOnDragListener(ecouteur);
            }
        }

        //Ecouteurs sur les frames de cartes à déposer
        LinearLayout cards_to = findViewById(R.id.cards_to);
        for (int i = 0; i < cards_to.getChildCount(); i++) {
            //Pour chaque layer

            //View child = cards_to.getChildAt(i);

            if(cards_to.getChildAt(i) instanceof LinearLayout){
                LinearLayout layer = (LinearLayout) cards_to.getChildAt(i);

                for (int j = 0; j < layer.getChildCount(); j++) {
                    //View child2 = layer.getChildAt(j);

                    if(layer.getChildAt(j) instanceof LinearLayout) {
                        LinearLayout temp = (LinearLayout) layer.getChildAt(j);
                        LinearLayout frame = (LinearLayout) temp.getChildAt(0);
                        ConstraintLayout co = (ConstraintLayout) frame.getChildAt(0);
                        co.setOnDragListener(ecouteur);
                    }
                }
            }
        }

        menu_btn = findViewById(R.id.fn_menu);
        menu_btn.setOnClickListener(ecouteur);



    }




    /** Méthode pour initialiser le pacquet de carte
     *
     */
    private void init_cartes(){
        carte_restantes = new Vector<>();
        for (int i = 1; i <= 97; i++) {
            carte_restantes.add(new Carte(i));
        }
    }

    /** Méthode pour ajouter des cartes la ou il y en a pas
     *
     */

    private void remplir_cartes(){
        LinearLayout cards_from = findViewById(R.id.cards_from);
        for (int i = 0; i < cards_from.getChildCount(); i++) {
            //Pour chaque colonne
            LinearLayout colonne = (LinearLayout) cards_from.getChildAt(i);
            for (int j = 0; j < colonne.getChildCount(); j++) {
                //Pour chaque item par rangée
                LinearLayout item = (LinearLayout) colonne.getChildAt(j);
                ConstraintLayout conteneur_carte = (ConstraintLayout) item.getChildAt(0);
                if(conteneur_carte.getChildAt(0) == null) {
                    //ajouter carte depuis le deck
                    TextView carte = Carte.get_format(MainActivity.this, 0);
                    conteneur_carte.addView(carte);
                    carte.setOnTouchListener(ecouteur);
                }
            }
        }

    }



    class Ecouteur implements View.OnClickListener, View.OnDragListener, View.OnTouchListener {

        View carte = null;
        @Override
        public void onClick(View v) {

            if(v == menu_btn){
                finish();
            }

        }

        @Override
        public boolean onDrag(View v, DragEvent event) {

            switch (event.getAction()) {
                case DragEvent.ACTION_DROP:
                    carte = (View) event.getLocalState();
                    try {
                        if (v.getTag().toString().equals("droppable")) {

                            ConstraintLayout parent = (ConstraintLayout) carte.getParent();
                            parent.removeView(carte);

                            ConstraintLayout case_carte = (ConstraintLayout) v;
                            case_carte.addView(carte);
                            //carte.setVisibility(View.VISIBLE);
                            //carte.setOnTouchListener(null);
                            //carte = null;
                        }
                        else {
                            carte.setVisibility(View.VISIBLE);
                            carte = null;
                        }
                    } catch (NullPointerException npe) {
                        if (carte != null) {
                            carte.setVisibility(View.VISIBLE);
                            carte = null;
                        }
                    }
                    break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if(carte != null && carte.getVisibility() == View.INVISIBLE){
                            carte.setVisibility(View.VISIBLE);
                            carte.setOnTouchListener(null);
                            carte = null;
                        }

            }


            return true;
        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            View.DragShadowBuilder builder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(null,builder,v,0);
            v.setVisibility(View.INVISIBLE);


            return true;
        }
    }


}