package com.CaronCorp.tp1;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class DialogLargeur extends Dialog {
    MainActivity mainWindow;
    Ecouteur ec;

    SeekBar largeur;
    Button sendButton;

    int largeurTrait;
    TextView valeur;


    public DialogLargeur(@NonNull Context context) {
        super(context);
        mainWindow = (MainActivity) context;
        //Log.i("progresse", "DialogLargeur: "+mainWindow.getLargeurTrait());

        largeurTrait = mainWindow.getLargeurTrait();

    }

    protected void updateText(int progress){

        String temp = valeur.getText().toString();
        valeur.setText(temp.substring(0,18)+progress);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_largeur);

        largeur = findViewById(R.id.selecteur);
        largeur.setMin(1);
        largeur.setMax(50);



        sendButton = findViewById(R.id.button);
        valeur = findViewById(R.id.textView);
        largeurTrait = mainWindow.getLargeurTrait();

        largeur.setProgress(largeurTrait);
        updateText(largeurTrait);



        ec = new Ecouteur();

        sendButton.setOnClickListener(ec);

        largeur.setOnSeekBarChangeListener(ec);
    }
    private class Ecouteur implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{

        @Override
        public void onClick(View source) {
            mainWindow.setLargeurTrait(largeur.getProgress());
            dismiss();
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            DialogLargeur.this.updateText(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }




}