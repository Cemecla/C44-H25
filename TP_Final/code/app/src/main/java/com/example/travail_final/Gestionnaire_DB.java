package com.example.travail_final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Vector;

public class Gestionnaire_DB extends SQLiteOpenHelper {
    private static Gestionnaire_DB instance;
    private SQLiteDatabase database;

    public static Gestionnaire_DB getInstance(Context context){
        if ( instance == null ) {
            instance = new Gestionnaire_DB(context);
        }
        return instance;
    }

    private Gestionnaire_DB(@Nullable Context context) {
        super(context, "bd", null, 1);
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
        String creation_table = "CREATE TABLE Game_Stats (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "cartes_restantes INTEGER NOT NULL,"+
                "temps TEXT NOT NULL,"+
                "score INTEGER NOT NULL)";

        db.execSQL(creation_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Game_Stats");
        onCreate(db);
    }

    public void ajouter_enregistrement(int cartes,String temps, int score){
        ContentValues cv = new ContentValues();

        // a voir pour ajouter un convertisseur de string en int pour le temps
        cv.put("cartes_restantes",cartes);
        cv.put("temps",temps);
        cv.put("score",score);

        database.insert("Game_Stats",null,cv);
    }

    public Vector<String> remplirSpinner_par_score(){
        Vector<String> resultats = new Vector<>();
        Cursor c = database.rawQuery("SELECT score, cartes_restantes, temps FROM Game_Stats ORDER BY score DESC LIMIT 3",null);
        while (c.moveToNext()){
            int score = c.getInt(0);
            int cartes = c.getInt(1);
            String temps = c.getString(2);
            resultats.add("Score: "+score + " Cartes restantes: " + cartes + " Temps: " + temps);
        }
        c.close();
        return resultats;

    }
}
