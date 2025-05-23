package com.example.annexe15;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    Spinner liste;

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

        instance = Gestionnaire_BD.getInstance(getApplicationContext());
        instance.ouvrirConnexion();
        liste = findViewById(R.id.spinnertkt);

        remplirSpinner(instance.remplirSpinner());

    }



    public void remplirSpinner(Vector<String> v){
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,v);
        liste.setAdapter(adapter);
    }

}