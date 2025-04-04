package com.CaronCorp.tp1;

import android.gesture.GestureStroke;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class TraceLibre extends Forme {
    private Path chemin;


    public TraceLibre(String couleur, int strokeWidth,Path p) {
        super(couleur, strokeWidth);
        this.chemin = new Path(p);
    }

    public Path getChemin() {
        return chemin;
    }

    @Override
    public void dessiner(Canvas canvas, Paint pinceau, String backgroundTag) {
        configurerPinceau(pinceau, backgroundTag);
        canvas.drawPath(chemin, pinceau);
    }

    @Override
    public void dessinerPreview(Canvas canvas, Paint pinceau) {
        canvas.drawPath(chemin, pinceau);
    }


}
