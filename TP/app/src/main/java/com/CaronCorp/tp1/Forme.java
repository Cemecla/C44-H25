package com.CaronCorp.tp1;

import android.accessibilityservice.GestureDescription;
import android.gesture.GestureStroke;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class Forme {
    private String couleur;
    private int strokeWidth;
    private boolean isStroke;

    public Forme(String couleur, int strokeWidth) {
        this.couleur = couleur;
        this.strokeWidth = strokeWidth;
        this.isStroke = true;
    }

    protected void configurerPinceau(Paint pinceau, String background) {
        pinceau.reset();


        if(couleur.equals("#Background")) {
            pinceau.setColor(Color.parseColor(background));
        } else {
            pinceau.setColor(Color.parseColor(couleur));
        }

        pinceau.setStrokeWidth(strokeWidth);
        pinceau.setStrokeJoin(Paint.Join.ROUND);
        pinceau.setStrokeCap(Paint.Cap.ROUND);

        if (isStroke) {
            pinceau.setStyle(Paint.Style.STROKE);
        } else {
            pinceau.setStyle(Paint.Style.FILL);
        }
    }

    public abstract void dessiner(Canvas canvas, Paint pinceau, String background);
    public abstract void dessinerPreview(Canvas canvas, Paint pinceau);


    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }
}
