package com.CaronCorp.tp1;

import android.gesture.GestureStroke;
import android.graphics.Path;

public class Triangle extends Forme{
    private Path chemin;
    public Triangle(String couleur, int strokeWidth, Path path) {
        super(couleur, strokeWidth);
        chemin = new Path(path);
    }

    public Path getChemin() {
        return chemin;
    }
}
