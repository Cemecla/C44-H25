package com.example.annexe9;



import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.DecimalFormat;

public class PlacementActivity extends AppCompatActivity {

    private EditText champMontant;
    private NumberPicker numberPicker;
    private TextView labelReponse;
    private Button bouton;

    private Placement placement;

    private Ecouteur ec;



    public DecimalFormat d = new DecimalFormat("#,###.##$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement);

        champMontant =  findViewById(R.id.champMontant);
        numberPicker = findViewById(R.id.numberPicker);
        labelReponse =  findViewById(R.id.labelReponse);
        bouton = findViewById(R.id.bouton);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
        NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                int temp = value * 12;
                return "" + temp;
            }
        };


        numberPicker.setFormatter(formatter);
        
        // 3 étapes
        ec = new Ecouteur();

        bouton.setOnClickListener(ec);



    }


    private class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            int nbrMois = numberPicker.getValue() * 12;

            try {
                double montant = Double.parseDouble(champMontant.getText().toString()); // Peut lancer un number FormatException si on entre du texte au lieu d'un nombre
                placement = new Placement(montant,nbrMois);
                double resultat = placement.calculerMontantFinal();
                labelReponse.setText(d.format(resultat) );
            } catch (NumberFormatException nfe){
                creerAlertDialog("Recommencer en entrant un montant valide");
                champMontant.setText("");
                champMontant.requestFocus();
                champMontant.setHint("Entrez un nombre, ex: 1000");
                labelReponse.setText("");
            }
            catch (Exception nfe){
                // Ce ne sera pas un numberformatException, C'est une généralisation pour dire toutes les exceptions...
            }
        }
    }

    //pour créer une boite de dialogue simple
    public void creerAlertDialog(String message) {


        AlertDialog.Builder builder = new AlertDialog.Builder(PlacementActivity.this);

        //on peut faire ca !!
        builder.setMessage(message)
                .setTitle("Erreur");


        AlertDialog dialog = builder.create();
        dialog.show();
    }
}








