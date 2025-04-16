package com.example.applicationannexe11bcapitales;


import java.util.Hashtable;
import java.util.Objects;

import bla.HashtableAssociation;

public class Inscrit {
    private String nom;
    private String prenom;
    private String adresse;
    private String capitale;
    private String etat;
    private String codeZip;

    public Inscrit(String nom, String prenom, String adresse, String capitale, String etat, String codeZip)throws AdresseException
    {



        HashtableAssociation asso = new HashtableAssociation();

        // vérifier si la capitale fait partie de l'état à l'aide d'une Hashtable secrète ( classe HashtableAssociation )
        String etatRetout = asso.get(capitale);
        if(! etatRetout.equals(etat))
            throw new AdresseException(capitale,etat);

        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.codeZip = codeZip;
        this.capitale = capitale;
        this.etat = etat;



    }
}
