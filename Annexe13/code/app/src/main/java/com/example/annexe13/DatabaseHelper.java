package com.example.annexe13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Vector;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;
    private SQLiteDatabase database;
    public static final String TABLE_NAME = "evaltable";
    public static final String COL_NOM = "nom";
    public static final String COL_MICRO = "micro";
    public static final String COL_ETOILE = "etoile";

    public static DatabaseHelper getInstance(Context contexte) {
        if ( instance == null ) {
            instance = new DatabaseHelper(contexte);
        }
        return instance;
    }

    private DatabaseHelper(@Nullable Context context) {
        super(context, "bd", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOM + " TEXT NOT NULL, " +
                COL_MICRO + " TEXT NOT NULL, " +
                COL_ETOILE + " FLOAT NOT NULL)";
        db.execSQL(CREATE_TABLE);
    }

    public void ajouterEval(Evaluation eval) {
        if (database == null || !database.isOpen()) {
            ouvrirConnexion(); // s'assurer que la base est bien ouverte
        }

        ContentValues values = new ContentValues();
        values.put(COL_NOM, eval.getNom());
        values.put(COL_MICRO, eval.getMicro());
        values.put(COL_ETOILE, eval.getEval());

        database.insert(TABLE_NAME, null, values);
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

    public Vector<Evaluation> getEval() {
        Vector<Evaluation> liste = new Vector<>();
        // cur = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        Cursor cur = database.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_ETOILE + " DESC LIMIT 3", null);

        while (cur.moveToNext()) {
            String nom = cur.getString(1);
            String orig = cur.getString(2);
            float stars = cur.getFloat(3);

            //String ligne = "id " + cur.getString(0) + ", Nom: " + cur.getString(1) + ", Micro: " + cur.getString(2) + ", Étoile: " + cur.getInt(3);
            liste.add(new Evaluation(nom,orig,stars));
        }

        cur.close();
        return liste;
    }
}
