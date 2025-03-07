package com.CaronCorp.tp1;

import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView btnLargeurTrait;
    int largeurTrait;


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

        btnLargeurTrait = findViewById(R.id.taille_trait_img);


        //
        Ecouteur ec = new Ecouteur();
        btnLargeurTrait.setOnClickListener(ec);


    }

    public int getLargeurTrait() {
        return largeurTrait;
    }

    public void setLargeurTrait(int largeurTrait) {
        this.largeurTrait = largeurTrait;
    }


    private class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View source) {
            // on peut faire apparaitre une boite de dialogue
            DialogLargeur dialog = new DialogLargeur(MainActivity.this);
            dialog.show();
        }
    }



}