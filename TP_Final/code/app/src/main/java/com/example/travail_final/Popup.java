package com.example.travail_final;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class Popup extends Dialog {
    MainActivity mainWindow;
    Ecouteur ec;

    TextView titre;
    Button retour;
    Button retry;

    public Popup(@NonNull Context context) {
        super(context);
        mainWindow = (MainActivity) context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);


        titre = findViewById(R.id.popup_titre);
        retour = findViewById(R.id.popup_retour);
        retry = findViewById(R.id.popup_retry);


        ec = new Ecouteur();

        retry.setOnClickListener(ec);
        retour.setOnClickListener(ec);
    }

    public void setTitre(String title_text){
        titre.setText(title_text);
    }

    class Ecouteur implements View.OnClickListener{


        @Override
        public void onClick(View view) {
            if(view == retour){
                mainWindow.retour_menu();
            }
            else{

            }

        }
    }


}





