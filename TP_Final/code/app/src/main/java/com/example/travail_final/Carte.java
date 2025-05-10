package com.example.travail_final;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.AttributedCharacterIterator;
import java.util.jar.Attributes;

public class Carte {
        private int numero;
        private String couleur;



        public Carte(int numero) {
                this.numero = numero;
        }


        public int getNumero() {
                return numero;
        }

        public boolean est_superieur_a(Carte carte){
                return carte.getNumero() < this.numero;
        }

        public boolean est_inferieure_a(Carte carte){
                return carte.getNumero() > this.numero;
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        public static TextView get_format(Context c, int num){
                TextView temp = new TextView(c);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                temp.setLayoutParams(layoutParams);
                //Padding (unité en dp , valeur , selon_l'ecran)
                int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,c.getResources().getDisplayMetrics());
                temp.setPadding(0,0,padding,0);
                temp.setTypeface(Typeface.DEFAULT_BOLD);
                temp.setBackground(c.getResources().getDrawable(R.drawable.card,null));
                temp.setGravity(Gravity.TOP|Gravity.RIGHT);
                temp.setText(String.valueOf(num));

                return temp;
        }

}



