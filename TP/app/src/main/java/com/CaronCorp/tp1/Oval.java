package com.CaronCorp.tp1;

import android.gesture.GestureStroke;
import android.graphics.Point;

public class Oval extends Forme{

    private int rayon;
    private Point centre;
    public Oval(String couleur, int strokeWidth, boolean stroke, int rayon, Point centre) {
        super(couleur, strokeWidth, stroke);
        this.centre = centre;
        this.rayon = rayon;
    }

    public int getRayon() {
        return rayon;
    }

    public Point getCentre() {
        return centre;
    }
}
