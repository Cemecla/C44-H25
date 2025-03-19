package com.CaronCorp.tp1;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView btn_LargeurTrait;

    ImageView btn_Crayon;

    ConstraintLayout zone_dessin;

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
        zone_dessin = findViewById(R.id.zone_dessin);


        //
        Ecouteur ec = new Ecouteur();
        btn_LargeurTrait.setOnClickListener(ec);
        //btn_Crayon.setOnClickListener(ec);
        zone_dessin.setOnTouchListener(ec);



    }

    public int getLargeurTrait() {
        return largeurTrait;
    }

    public void setLargeurTrait(int largeurTrait) {
        this.largeurTrait = largeurTrait;
    }


    private class Ecouteur implements View.OnClickListener, View.OnTouchListener {

        @Override
        public void onClick(View source) {
            // on peut faire apparaitre une boite de dialogue

            if(source == btn_LargeurTrait){
                DialogLargeur dialog = new DialogLargeur(MainActivity.this);
                dialog.show();
            }



        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float tempx = event.getX();
            float tempy =event.getY();


            //if(event.isButtonPressed(MotionEvent.ACTION_DOWN)){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
//                Log.i("ruwurrg", "onTouch: "+tempx);
//                Log.i("ruwurrg", "onTouch: "+tempy);
            }


            return true;
        }
    }



}