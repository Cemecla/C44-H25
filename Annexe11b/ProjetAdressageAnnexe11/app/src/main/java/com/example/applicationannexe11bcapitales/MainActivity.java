package com.example.applicationannexe11bcapitales;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;


import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import bla.HashtableAssociation;


public class MainActivity extends AppCompatActivity {

    EditText champPrenom, champNom, champAdresse, champZip;
    Spinner spinnerCapitale, spinnerEtat;

    Button bouton;

    Inscrit personne;


    Ecouteur ec;

    HashtableAssociation asso;

    String str_etat;

    String str_capital;

    Vector<String> captiale;
    Vector<String>  etat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        champPrenom = findViewById(R.id.champPrenom);
        champNom= findViewById(R.id.champNom);
        champAdresse = findViewById(R.id.champAdresse);
        champZip = findViewById(R.id.champZip);

        spinnerCapitale = findViewById(R.id.spinnerCapitale);
        spinnerEtat = findViewById(R.id.spinnerEtat);

        bouton = findViewById(R.id.boutonInscrire);

        // remplir les spinner à l'aide de la Hashtable

        asso = new HashtableAssociation();



        captiale = new Vector();
        captiale.addAll(asso.keySet());
        etat = new Vector<>();
        etat.addAll(asso.values());

        Collections.sort(captiale);
        Collections.sort(etat);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,captiale);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,etat);

        spinnerCapitale.setAdapter(adapter);
        spinnerEtat.setAdapter(adapter2);
        ec = new Ecouteur();
        bouton.setOnClickListener(ec);

        spinnerEtat.setOnItemSelectedListener(ec);
        spinnerCapitale.setOnItemSelectedListener(ec);



    }
    private class Ecouteur implements View.OnClickListener, AdapterView.OnItemSelectedListener
    {

        @Override
        public void onClick(View v) {

            String nom = champNom.getText().toString();
            String prenom = champPrenom.getText().toString();
            String adresse = champAdresse.getText().toString();
            String zip = champZip.getText().toString();




            try{
                personne = new Inscrit(nom,prenom,adresse,str_capital,str_etat,zip);
                //str_capital peux également directement appeler spinnerCapitale.getSelectedItem()

            }
            catch (AdresseException ae){
                creerAlertDialog(ae.getMessage());
            }


        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if(parent == spinnerCapitale)
                str_capital = captiale.get(position);
            else
            if(parent == spinnerEtat)
                str_etat = etat.get(position);

            else {
                str_capital = captiale.get(0);
                str_etat = etat.get(0);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void creerAlertDialog(String message) {


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        //on peut faire ca !!
        builder.setMessage(message)
                .setTitle("Erreur");


        AlertDialog dialog = builder.create();
        dialog.show();
    }
}