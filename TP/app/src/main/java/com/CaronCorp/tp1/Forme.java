package com.CaronCorp.tp1;

import android.accessibilityservice.GestureDescription;
import android.gesture.GestureStroke;
import android.graphics.Canvas;

public class Forme {
    private int couleur;
    private int strokeWidth;

    private GestureStroke stroke;

    public Forme(int couleur, int strokeWidth, GestureStroke stroke) {
        this.couleur = couleur;
        this.strokeWidth = strokeWidth;
        this.stroke = stroke;
    }
}
