package com.example.travail_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;

public class Menu extends AppCompatActivity {

    Button play_btn;
    Ecouteur ec;

    ListView liste;
    Gestionnaire_DB instance;
    String type_choisi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        type_choisi = "score";
        instance = Gestionnaire_DB.getInstance(getApplicationContext());
        instance.ouvrirConnexion();



        liste = findViewById(R.id.scoreboard);
        chargerDonnees();

        ec = new Ecouteur();

        play_btn = findViewById(R.id.play_btn);

        play_btn.setOnClickListener(ec);
    }

    private void chargerDonnees(){
        instance.ouvrirConnexion();

        Vector<String> donnees = instance.remplirSpinner_par_score();

        if (donnees != null && !donnees.isEmpty()) {
            remplirListe(type_choisi, donnees);
        }
        instance.fermerConnexion();

    }

    private void remplirListe(String type, Vector<String> v){
        if(type.equals("score")){
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,v);
            liste.setAdapter(adapter);
        }
    }



    class Ecouteur implements View.OnClickListener {


        @Override
        public void onClick(View view) {
            Intent i = new Intent(Menu.this,MainActivity.class);

            startActivity(i);
        }
    }


}