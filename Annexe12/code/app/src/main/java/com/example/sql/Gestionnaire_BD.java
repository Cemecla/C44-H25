package com.example.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
// C'est un singleton, car on veut juste au maximum une instance de cette classe
public class Gestionnaire_BD extends SQLiteOpenHelper {

    private static Gestionnaire_BD instance;
    private Gestionnaire_BD(@Nullable Context context) {
        super(context, "DB", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE Inventeur(_id INTEGER PRIMARY KEY AUTOINCREMENT,nom TEXT,origine TEXT,invention TEXT,annee TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static Gestionnaire_BD getInstance(Context context) {
        if(instance == null)
            instance = new Gestionnaire_BD(context);
        return instance;
    }


}
