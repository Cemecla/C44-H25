package com.example.annexe15;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Vector;

public class Gestionnaire_BD extends SQLiteOpenHelper {
    private static Gestionnaire_BD instance;
    private SQLiteDatabase database;
    private static final String TABLE_NAME = "EquipesLHJMQ";
    private static final String COL_NOM = "nom";
    private static final String COL_DIVISION = "division";
    private static final String COL_ARENA = "arena";
    private static final String COL_Capacite = "capacité";

    public static Gestionnaire_BD getInstance(Context context){
        if ( instance == null ) {
            instance = new Gestionnaire_BD(context);
        }
        return instance;
    }

    private Gestionnaire_BD(@Nullable Context context) {
        super(context, "bd", null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS inventeur");
        onCreate(db);
    }


    public void ouvrirConnexion(){
        if (database == null || !database.isOpen()) {
            database = this.getWritableDatabase();
        }
    }
    public void fermerConnexion(){
        if (database != null && database.isOpen()) {
            database.close();
        }
        database = null;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create1 = "CREATE TABLE " + TABLE_NAME+ " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_NOM+ " TEXT NOT NULL,"+
                COL_DIVISION+" TEXT NOT NULL,"+
                COL_ARENA+" TEXT NOT NULL,"+
                COL_Capacite+" TEXT NOT NULL)";

        db.execSQL(create1);
        //Création des objets
        ajouter_valeurs(new Equipe("Tigres de Victoriaville","Est",new Arena("Colisée Desjardins",1900)),db);
        ajouter_valeurs(new Equipe("Cataractes de Shawinigan","Est",new Arena("Centre Gervais Auto",4000)),db);
        ajouter_valeurs(new Equipe("Cataractes de Shawinigan","Ouest",new Arena("Centre Slush Puppie",4200)),db);
        ajouter_valeurs(new Equipe("Foreurs de Val d’Or","Ouest",new Arena("Centre Agnico Eagle",2600)),db);
        ajouter_valeurs(new Equipe("Armada de Blainville","Ouest",new Arena("Centre Rousseau",3000)),db);

    }

    public void ajouter_valeurs(Equipe e, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(COL_NOM,e.getNom());
        cv.put(COL_DIVISION,e.getDivision());
        cv.put(COL_ARENA,e.getArena().getNom());
        cv.put(COL_Capacite, e.getArena().getCapacite());

        db.insert(TABLE_NAME,null,cv);

    }

    public double moyenneCapacité(){
        Cursor c = database.rawQuery("SELECT avg("+COL_Capacite+") FROM "+TABLE_NAME,null);
        c.moveToNext();
        double moy = c.getDouble(0);
        c.close();
        return moy;
    }

    public Vector<String> remplirSpinner(){
        Vector<String> hahaha = new Vector<>();
        Cursor c = database.rawQuery("SELECT "+COL_ARENA+ " FROM EquipesLHJMQ",null);
        while (c.moveToNext()){
            hahaha.add(c.getString(0));
        }
        c.close();
        return hahaha;
    }

    public int get_EquipeOuest(){
        Cursor c = database.rawQuery("Select count(*) FROM "+TABLE_NAME+" WHERE "+ COL_DIVISION +" = 'Ouest'",null);
        int compteur = 0;
        if(c.moveToNext()){
            compteur = c.getInt(0);
        }
        c.close();
        return compteur;
    }

    public String trouverEquipeParArene(String areana){
        Cursor c = database.rawQuery("SELECT "+COL_NOM+" FROM "+TABLE_NAME+" WHERE "+COL_ARENA+" =?",new String[]{areana});
        String nom = "";
        while (c.moveToNext()){
            nom = c.getString(0);
        }
        c.close();
        return nom;

    }

    public Vector<Equipe> get_all(){
        Vector<Equipe> temp = new Vector<>();

        Cursor cur = database.rawQuery(" SELECT " + COL_NOM + "," + COL_DIVISION + "," + COL_ARENA + "," + COL_Capacite + " FROM "+ TABLE_NAME,null);

        while (cur.moveToNext()){
            String nom_equipe = cur.getString(1);
            String division = cur.getString(2);
            String nom_arena = cur.getString(3);
            int capacite = cur.getInt(4);

            temp.add(new Equipe( nom_equipe , division, new Arena(nom_arena,capacite)));
        }

        cur.close();
        return temp;

    }


}
