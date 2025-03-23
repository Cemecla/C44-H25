package com.CaronCorp.tp1;

import android.gesture.GestureStroke;
import android.graphics.Path;

public class TraceLibre extends Forme {
    private Path chemin;


    public TraceLibre(String couleur, int strokeWidth, boolean stroke,Path p) {
        super(couleur, strokeWidth, stroke);
        this.chemin = new Path(p);
    }

    public Path getChemin() {
        return chemin;
    }
}
