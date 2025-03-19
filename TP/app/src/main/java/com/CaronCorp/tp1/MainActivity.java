package com.CaronCorp.tp1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView btn_LargeurTrait;

    ImageView btn_Crayon;

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

        btn_LargeurTrait = findViewById(R.id.taille_trait_img);
        btn_Crayon = findViewById(R.id.crayon_img);
        largeurTrait = 1;


        //
        Ecouteur ec = new Ecouteur();
        btn_LargeurTrait.setOnClickListener(ec);
        btn_Crayon.setOnClickListener(ec);


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

            if(source == btn_LargeurTrait){
                DialogLargeur dialog = new DialogLargeur(MainActivity.this);
                dialog.show();
            }

            if(source == btn_Crayon){

            }

        }
    }



}