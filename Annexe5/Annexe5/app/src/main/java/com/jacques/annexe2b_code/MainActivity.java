package com.jacques.annexe2b_code;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    EditText champNomCompte;
    TextView champSolde;
    Button boutonValider;

    Vector<String> choix;

    Button boutonEnvoyer;
    EditText champSoldeDeTransaction;
    EditText getCompteExterne;

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

        champNomCompte = findViewById(R.id.from);
        champSolde = findViewById(R.id.from_sold);
        boutonValider = findViewById(R.id.from_valid);

        boutonEnvoyer = findViewById(R.id.send);
        getCompteExterne = findViewById(R.id.to);
        champSoldeDeTransaction = findViewById(R.id.to_qte);


        soldeFictif = 1400.0;

        choix = new Vector<>();
        choix.add("CHEQUE");
        choix.add("EPARGNE");
        choix.add("EPARGNEPLUS");

        // Étape 1:
        Ecouteur ec = new Ecouteur();
        // Étape 2:
        boutonValider.setOnClickListener(ec);
        // Étape 3:

        boutonEnvoyer.setOnClickListener(ec);

    }

    // Classe interne
    private class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View source) {// parametre : source de l'événement, boutons
            String compteChoisi = champNomCompte.getText().toString();
            //String compteChoisi = String.valueOf(champNomCompte.getText());  -- Meme chose
            compteChoisi = compteChoisi.toUpperCase();
            compteChoisi = compteChoisi.trim(); // enlever les espaces inutiles au debut ou à la fin du champ texte

            String CompteVers = getCompteExterne.getText().toString().toUpperCase().trim();


            if(source == boutonValider){
                if(choix.contains(compteChoisi)){
                    champSolde.setText("$ "+soldeFictif); // Voir aussi le decimalFormat
                }
                else{
                    // param type context = synonyme de l'activité - - - MainActivity.this car il faut d'abord aller chercher la classe -- this aurait chercher l'écouteur actuel
                    Toast.makeText(MainActivity.this, "Compte non existant !", Toast.LENGTH_SHORT).show();
                    compteChoisi = "";
                    champSolde.setText("");
                }
            }
            else{
                if(CompteVers.contains("@")){
                    String montant =  champSoldeDeTransaction.getText().toString();
                    double transfert = Double.parseDouble(montant);
                    if(transfert <= soldeFictif);
                        champSolde.setText(String.valueOf(soldeFictif));
                }
                else
                {
                    getCompteExterne.setText("Pas assez de fond");
                    champSolde.setText("0");
                }



            }



        }

    }
}