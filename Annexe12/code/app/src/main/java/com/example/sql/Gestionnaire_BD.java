package com.example.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Vector;

// C'est un singleton, car on veut juste au maximum une instance de cette classe
public class Gestionnaire_BD extends SQLiteOpenHelper {

    private static Gestionnaire_BD instance;

    //autres variables
    private SQLiteDatabase database;

    private Gestionnaire_BD(@Nullable Context context) {
        super(context, "DB", null, 1);
    }



    @Override // Appeller une seule fois lors de l'installation
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE inventeur(_id INTEGER PRIMARY KEY AUTOINCREMENT,nom TEXT,origine TEXT,invention TEXT,annee TEXT)");
            ajouterInventeur(new Inventeur("Laszlo Biro","Hongrie","Stylo à bille",1938),db);
            ajouterInventeur(new Inventeur("Benjamin Franklin","Etats-Unis","Paratonnerre",1752),db);
            ajouterInventeur(new Inventeur("Mary Anderson","Etat-Unis","Essuie-glace",1903),db);
            ajouterInventeur(new Inventeur("Grace Hopper","Etat-Unis","Compilateur",1952),db);
            ajouterInventeur(new Inventeur("Benoit Rouquayrot","France","Scaphandre",1864),db);

    }


    public void ajouterInventeur(Inventeur in,SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("nom",in.getNom());
        cv.put("origine",in.getOrigine());
        cv.put("invention",in.getInvention());
        cv.put("annee",in.getAnnee());

        db.insert("inventeur",null,cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Si jamais on demande une mise à jour, on va seulement
        db.execSQL("DROP TABLE IF EXISTS inveneteur");
        onCreate(db);
    }

    public void ouvrirConexion(){
        database = this.getWritableDatabase();
    }
    public void fermerConnexion(){
        database.close();

        //database = null; // à tester

    }

    public Vector<String> getInventions(){
        Vector<String> liste = new Vector<>();
        Cursor cur = database.rawQuery("SELECT invention FROM inventeur",null);

        while (cur.moveToNext())
        {
            liste.add( cur.getString(0) );
        }
        cur.close();

        return liste;
    }

    public boolean verifReponse(String nomInventeur, String invention){
        String requete = "SELECT * FROM inventeur WHERE nom = ? AND invention = ?";
        String[] tab = {nomInventeur,invention};

        Cursor cur = database.rawQuery(requete,tab);
        cur.close();
        return cur.moveToNext();

    }

    public static Gestionnaire_BD getInstance(Context context) {
        if(instance == null)
            instance = new Gestionnaire_BD(context);
        return instance;
    }


}
