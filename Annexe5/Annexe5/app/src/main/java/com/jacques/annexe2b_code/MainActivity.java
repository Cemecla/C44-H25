package com.jacques.annexe2b_code;

import static android.app.PendingIntent.getActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerNomCompte;
    TextView champSolde;


    Vector<String> choix;

    Button boutonEnvoyer;
    EditText champSoldeDeTransaction;
    EditText getCompteExterne;
    Hashtable<String, Compte> ht;

    DecimalFormat dl = new DecimalFormat("0.00$");
        // .settext(dl.format(solde))
    double soldeFictif = 0.0;

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

        spinnerNomCompte = findViewById(R.id.from);
        champSolde = findViewById(R.id.from_sold);


        boutonEnvoyer = findViewById(R.id.send);
        getCompteExterne = findViewById(R.id.to);
        champSoldeDeTransaction = findViewById(R.id.amount_send);

        ht = new Hashtable<>();

        ht.put ("c1", new Compte("Eparnge",25345));
        ht.put("c2", new Compte("Cheque",4560));
        ht.put("c3", new Compte("Epargne Plus", 50));


        choix = new Vector<>();

        //choix.addAll(ht.keySet());
        // on prend les clés et on les ajoute au vecteur, donc au spinner


        ht.entrySet().stream()
                .forEach(e -> choix.add(e.getValue().getNom().toString()) );

//        Collection<Compte> cpts = ht.values();
//        for(Compte c_temp: cpts){
//            choix.add(c_temp.getNom());
//        }

        //adaptateur pour remplir le spinner
        // this fait référence au Context et donc à l'activité de se fichier
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,choix);

        // Étape 1:
        Ecouteur ec = new Ecouteur();
        // Étape 2:
        spinnerNomCompte.setAdapter(adapter); // Lier le spinner avec l'adapter

    // Étape 3:

        boutonEnvoyer.setOnClickListener(ec);
        spinnerNomCompte.setOnItemSelectedListener(ec);


        // réucupérer les TextView sans écouteur d'événement.
        //getCompteExterne.setText(spinnerNomCompte.getSelectedItem().toString());

    }

    // Classe interne
    private class Ecouteur implements View.OnClickListener, AdapterView.OnItemSelectedListener {

        private Compte temp;

        @Override
        public void onClick(View source) {// parametre : source de l'événement, boutons



            String CompteVers = getCompteExterne.getText().toString().toUpperCase().trim();
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Erreur");

                if(CompteVers.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
                    String montant =  champSoldeDeTransaction.getText().toString();
                    double transfert = Double.parseDouble(montant);

                        if(temp.transfer(transfert))
                          champSolde.setText(dl.format(temp.getSolde()));
                        else
                        {
                            builder.setMessage("Manque de fonds");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                }
                else
                {
                    getCompteExterne.setText("Courriel éronné");
                    champSolde.setText("0");
                    builder.setMessage("Le courriel donné n'est pas valide");
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }


        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String str_compte_temp = choix.get(position);

            //String cle = (String)spinnerNomCompte.getItemAtPosition(position)
            //temp = ht.get(cle);
            //champSolde.setText(df.format(temp.getSolde()));

            // une hashtable est une collection qui est concu pour faire de la recherche plus rapidement

            temp = ht.entrySet().stream().filter(e -> e.getValue().getNom() == str_compte_temp)
                    .map(e -> e.getValue())
                    .findAny().get();

            double solde_temp = temp.getSolde();

//            double solde_temp = ht.entrySet().stream().filter(e -> e.getValue().getNom() == str_compte_temp)
//                    .map(e -> e.getValue().getSolde())
//                    .findFirst()
//                    .orElse(0.00);

            champSolde.setText(dl.format(solde_temp));

            // Solution 1
            //Toast.makeText(MainActivity.this, choix.get(position), Toast.LENGTH_SHORT).show();
            //////////////////////////////////////////////////////////////////////////////////////
            // Solution 2
            // Les items dans le Spinner sont des TextView
            // Donc voici un autre manière de faire le Toast
            //TextView choisi = (TextView)view;
            //Toast.makeText(MainActivity.this, choisi.getText().toString(), Toast.LENGTH_SHORT).show();
            ////////////////////////////////////////////////////////////////////////////////////////////////
            // Solution 3
//            String compte = spinnerNomCompte.getItemAtPosition(position).toString();
//            Toast.makeText(MainActivity.this, compte, Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}