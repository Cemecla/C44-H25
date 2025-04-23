package com.example.sql;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    ListView liste;
    Gestionnaire_BD instance;

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

        liste = findViewById(R.id.choix_reponse);

        instance = Gestionnaire_BD.getInstance(getApplicationContext()); // Avoir le contexte de toute l'application au lieu de la simple activité.
        instance.ouvrirConexion();
        Vector<String> choix = instance.getInventions();

        //remplir le ListView
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,choix);
        liste.setAdapter(arrayAdapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();
    }
}








