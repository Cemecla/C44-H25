package com.example.annexe13;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    Button afficher;
    Button ajouter;
    DatabaseHelper instance;

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
        Ecouteur ec = new Ecouteur();
        afficher = findViewById(R.id.buttonAfficher);

        afficher.setOnClickListener(ec);

        ajouter = findViewById(R.id.buttonAjout);
        ajouter.setOnClickListener(ec);

//        instance = DatabaseHelper.getInstance(getApplicationContext()); // Avoir le contexte de toute l'application au lieu de la simple activité.
//        instance.ouvrirConnexion();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //instance.fermerConnexion();
    }

    private class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == afficher) {
                Intent i = new Intent(MainActivity.this, VoirActivity.class);
                startActivity(i);
            }
            else {
                Intent i = new Intent(MainActivity.this, AjouterActivity.class);
                startActivity(i);
            }
        }
    }
}