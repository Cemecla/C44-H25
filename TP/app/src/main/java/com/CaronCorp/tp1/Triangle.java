package com.CaronCorp.tp1;

import android.gesture.GestureStroke;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class Triangle extends Forme{
    private Path chemin;
    public Triangle(String couleur, int strokeWidth, Path path) {
        super(couleur, strokeWidth);
        chemin = new Path(path);
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
