package com.CaronCorp.tp1;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DialogLargeur extends Dialog {
    MainActivity mainWindow;
    Ecouteur ec;

    public DialogLargeur(@NonNull Context context) {
        super(context);
        mainWindow = (MainActivity) context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_largeur);


        ec = new Ecouteur();
    }
    private class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View v) {

        }
    }


}