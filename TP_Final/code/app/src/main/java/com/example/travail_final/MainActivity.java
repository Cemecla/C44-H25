package com.example.travail_final;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
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
    TextView label_score;
    Chronometer label_chrono;

    Button menu_btn;

    ImageView undo;

    Pile [] piles;
    Pile last_action;

    Vector<ConstraintLayout> slots_sous_ecoutes;

    int score;


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
        label_score = findViewById(R.id.label_score);
        label_chrono = findViewById(R.id.label_temps);
        undo = findViewById(R.id.undo_img);
        update_labels();
        score = 0;


        slots_sous_ecoutes = new Vector<>();

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
                        slots_sous_ecoutes.add(co);
                    }
                }
            }
        }

        menu_btn = findViewById(R.id.fn_menu);
        menu_btn.setOnClickListener(ecouteur);
        undo.setOnClickListener(ecouteur);


    }

    private void run_chrono(){
        long elapsedRealtime = SystemClock.elapsedRealtime();
        label_chrono.setBase(elapsedRealtime);
        label_chrono.start();
    }

    private void update_labels(){

        // update du label du nombres de cartes
        int carte_posee = 0;
        LinearLayout cards_from = findViewById(R.id.cards_from);
        for (int i = 0; i < cards_from.getChildCount(); i++) {
            //Pour chaque colonne
            LinearLayout colonne = (LinearLayout) cards_from.getChildAt(i);
            for (int j = 0; j < colonne.getChildCount(); j++) {
                //Pour chaque item par rangée
                LinearLayout item = (LinearLayout) colonne.getChildAt(j);
                ConstraintLayout conteneur_carte = (ConstraintLayout) item.getChildAt(0);
                if(conteneur_carte.getChildAt(0) != null) {
                    carte_posee +=1;
                }
            }
        }
        int cartes_totales = carte_restantes.size() + carte_posee;

        label_cartes.setText(String.valueOf(cartes_totales));

        int total_points = 0;
        for (Pile p: piles) {
            total_points += p.get_valeur();
        }
        total_points-=200;
        label_score.setText(String.valueOf(total_points));

        if(cartes_totales < 97 && !label_chrono.isActivated()){
            run_chrono();
        }

        // update du label du score

    }




    /** Méthode pour initialiser le paquet de carte
     *
     */
    private void init_cartes(){
        carte_restantes = new Vector<>();
        for (int i = 1; i < 98; i++) {
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
                        Carte c_temp = carte_restantes.remove(0);
                        TextView carte = Carte.get_format(MainActivity.this, c_temp.getNumero());
                        conteneur_carte.addView(carte);
                        carte.setOnTouchListener(ecouteur);
                    }
                }
            }
        }

    }

    private void gestion_enregistrement_mouvements(Pile pile_temp){
        // Si il y a eu deux actions
        if(last_action == null){
            last_action = pile_temp;
        }else{
            last_action = null;
            remplir_cartes();
        }
    }

    private void undo(){
        if(last_action != null){
            Animation rotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation_animation);
            undo.startAnimation(rotateAnimation);
            Carte c = last_action.retirer_carte();
            for (int i = 0; i < piles.length; i++) {
                if(piles[i].getDerniereCarte().equals(c))
                {
                    ConstraintLayout slot = slots_sous_ecoutes.get(i);
                    slot.removeAllViews();
                    
                }
            }
        }
    }


    class Ecouteur implements View.OnClickListener, View.OnDragListener, View.OnTouchListener {

        View carte_joue = null;
        Pile pile_temp;

        @Override
        public void onClick(View v) {

            if (v == menu_btn) {
                finish();
            }
            if(v == undo){

                undo();
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

                            // essayer de voir pour placer les Views dans un Vector à la place de devoir appeler les 2 boucles constamment

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

                                gestion_enregistrement_mouvements(pile_temp);

                                update_labels();

                            }


                        }

                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    if (v instanceof ConstraintLayout){
                        carte_joue.setVisibility(View.VISIBLE);
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

    @Override
    protected void onStop() {

        super.onStop();
    }
}