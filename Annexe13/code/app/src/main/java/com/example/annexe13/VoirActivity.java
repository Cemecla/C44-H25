package com.example.annexe13;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;

public class VoirActivity extends AppCompatActivity {

    ListView best_of_best;

    DatabaseHelper instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voir);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        best_of_best = findViewById(R.id.liste);

        instance = DatabaseHelper.getInstance(getApplicationContext());
        instance.ouvrirConnexion();

        Vector<Evaluation> top_Eval = instance.getEval();
        Vector<String> record = new Vector<>();
        top_Eval.forEach(evaluation -> {record.add(evaluation.getNom()+" "+evaluation.getEval());});

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,record);
        best_of_best.setAdapter(arrayAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();
    }




}