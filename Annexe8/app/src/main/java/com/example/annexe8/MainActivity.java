package com.example.annexe8;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class    MainActivity extends AppCompatActivity {

    ChipGroup groupe;


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

        groupe = findViewById(R.id.group);

        Ecouteur ec = new Ecouteur();


        for (int i = 0; i < groupe.getChildCount(); i++) {
            Chip temp = (Chip) groupe.getChildAt(i);
            temp.setOnCheckedChangeListener(ec);
        }

    }

    private class Ecouteur implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if(isChecked){ // Puisqu'ils agissent comme des RadioButtons, il y aura toujours 2 chip qui changeront d'états
                String couleur = buttonView.getTag().toString();

                ConstraintLayout temp = findViewById(R.id.showchoice);
                temp.setBackgroundColor(Color.parseColor(couleur));
                }
        }
    }
}