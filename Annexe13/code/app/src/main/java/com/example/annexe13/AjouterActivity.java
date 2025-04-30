package com.example.annexe13;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AjouterActivity extends AppCompatActivity {

    Button load;
    EditText input_nom;
    EditText input_origine;
    RatingBar bar;
    DatabaseHelper instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ajouter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        instance = DatabaseHelper.getInstance(getApplicationContext());
        instance.ouvrirConnexion();

        input_nom = findViewById(R.id.input_nom);
        input_origine = findViewById(R.id.input_origine);

        bar = findViewById(R.id.ratingBar);

        load = findViewById(R.id.enregistrer);



        Ecouteur ec = new Ecouteur();
        load.setOnClickListener(ec);

    }

    @Override
    protected void onStop() {
        instance.fermerConnexion();
        super.onStop();

    }

    private void close_activity() {
        instance.fermerConnexion();
        finish();
    }

    private class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View v) {


            if(v == load){
                String nom = input_nom.getText().toString();
                String orig = input_origine.getText().toString();
                float stars = bar.getRating();
                Evaluation eval = new Evaluation(nom,orig,stars);

                instance.ajouterEval(eval);

                close_activity();
            }


//            if ( v == quitt ) {
//                finish();
//            }
//            if ( v == ajouter1 ) {
//                Evaluation e = new Evaluation("test", "test2", 3);
//                instance.ajouterEval(e);
//                System.out.println(instance.getEval());
//            }
        }
    }
}