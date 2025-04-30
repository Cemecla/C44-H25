package com.example.annexe15;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Gestionnaire_BD extends SQLiteOpenHelper {
    private static Gestionnaire_BD instance;
    private SQLiteDatabase database;
    private static final String TABLE_NAME = "EquipesLHJMQ";
    private static final String COL_NOM = "nom";
    private static final String COL_DIVISION = "Division";
    private static final String COL_ARENA = "Aréna";
    private static final String COL_Capacite = "Capacité";

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
    }



}
