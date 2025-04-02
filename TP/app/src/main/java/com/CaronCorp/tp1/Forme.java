package com.CaronCorp.tp1;

import android.accessibilityservice.GestureDescription;
import android.gesture.GestureStroke;
import android.graphics.Canvas;

public class Forme {
    private String couleur;
    private int strokeWidth;

    private boolean isStroke;

    public Forme(String couleur, int strokeWidth, boolean isStroke) {
        this.couleur = couleur;
        this.strokeWidth = strokeWidth;
        this.isStroke = isStroke;
    }

    public void dessiner(){

    }

    public String getCouleur() {
        return couleur;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public boolean isStroke() {
        return isStroke;
    }
}
