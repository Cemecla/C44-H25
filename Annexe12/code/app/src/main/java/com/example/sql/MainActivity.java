package com.example.sql;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    ListView liste;
    Gestionnaire_BD instance;

    Ecouteur ec;
    TextView Reponse_retour;

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

        Reponse_retour = findViewById(R.id.retour_reponse);
        ec = new Ecouteur();

        liste = findViewById(R.id.choix_reponse);

        instance = Gestionnaire_BD.getInstance(getApplicationContext()); // Avoir le contexte de toute l'application au lieu de la simple activité.
        instance.ouvrirConnexion();
        Vector<String> choix = instance.getInventions();

        //remplir le ListView
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,choix);
        liste.setAdapter(arrayAdapter);


        liste.setOnItemClickListener(ec);
    }

    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();
    }



    private class Ecouteur implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String retour = (String) parent.getItemAtPosition(position);

            if (instance.verifReponse("Mary Anderson",retour)){
                Reponse_retour.setText("Bonne Réponse");
            }
            else{
                Reponse_retour.setText("Désolé, tu es mauvais!");
            }
        }
    }
}








