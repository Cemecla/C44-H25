package com.example.travail_final;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Collections;
import java.util.Vector;


public class MainActivity extends AppCompatActivity {


    Vector<Carte> carte_restantes;
    Ecouteur ecouteur;

    TextView label_cartes;

    Button menu_btn;

    Pile [] piles;

    Pile [] record_actions;


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
        piles = new Pile[]{new Pile("ASC"), new Pile("ASC"), new Pile("DESC"), new Pile("DESC")};

        init_cartes();
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


    private void update_labels(){
        label_cartes.setText((carte_restantes.size())+"");
    }



    /** Méthode pour initialiser le paquet de carte
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
                    if (!carte_restantes.isEmpty()) {
                        //ajouter carte depuis le deck
                        Collections.shuffle(carte_restantes);
                        Carte c_temp = carte_restantes.firstElement();
                        TextView carte = Carte.get_format(MainActivity.this, c_temp.getNumero());
                        conteneur_carte.addView(carte);
                        carte.setOnTouchListener(ecouteur);
                    }
                }
            }
        }

    }

    private boolean




    class Ecouteur implements View.OnClickListener, View.OnDragListener, View.OnTouchListener {

        View carte_joue = null;
        Pile pile_temp;

        @Override
        public void onClick(View v) {

            if (v == menu_btn) {
                finish();
            }

        }

        @Override
        public boolean onDrag(View v, DragEvent event){

            switch (event.getAction()) {
                case DragEvent.ACTION_DROP:
                    carte_joue = (View) event.getLocalState();
                    if(!(v.getTag() == null)) {
                        switch (Integer.parseInt(v.getTag().toString())) {
                            case 0:
                                pile_temp = piles[0];
                                break;
                            case 1:
                                pile_temp = piles[1];
                                break;
                            case 2:
                                pile_temp = piles[2];
                                break;
                            case 3:
                                pile_temp = piles[3];
                                break;
                            default:
                                return false;
                        }

                        if (v instanceof ConstraintLayout) {


                            //logique de jeu
                            int valeur_carte = Integer.parseInt(((TextView)carte_joue).getText().toString());
                            Carte temp_carte = new Carte(valeur_carte);
                            if(pile_temp.isCarteValide(temp_carte)){
                                ConstraintLayout parent = (ConstraintLayout) carte_joue.getParent();
                                parent.removeView(carte_joue);

                                ConstraintLayout case_carte = (ConstraintLayout) v;
                                case_carte.removeAllViews();
                                case_carte.addView(carte_joue);
                                carte_joue.setOnTouchListener(null);
                                pile_temp.add_carte(temp_carte);
                            }


                        }

                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    if (v instanceof ConstraintLayout){
                        carte_joue.setVisibility(View.VISIBLE);
                        //carte_joue = null;
                    }
                    break;

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