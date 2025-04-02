package com.CaronCorp.tp1;

import android.gesture.GestureStroke;
import android.graphics.Path;

public class Triangle extends Forme{
    private Path chemin;
    public Triangle(String couleur, int strokeWidth, boolean stroke, Path path) {
        super(couleur, strokeWidth, stroke);
        chemin = new Path(path);
    }

    public Path getChemin() {
        return chemin;
    }
}
