package com.CaronCorp.tp1;

import android.gesture.GestureStroke;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Oval extends Forme{

    private int rayon;
    private Point centre;
    public Oval(String couleur, int strokeWidth, int rayon, Point centre) {
        super(couleur, strokeWidth);
        this.centre = centre;
        this.rayon = rayon;
    }


    @Override
    public void dessiner(Canvas canvas, Paint pinceau, String backgroundTag) {
        configurerPinceau(pinceau, backgroundTag);
        canvas.drawCircle(centre.x, centre.y, rayon, pinceau);
    }

    @Override
    public void dessinerPreview(Canvas canvas, Paint pinceau) {
        canvas.drawCircle(centre.x, centre.y, rayon, pinceau);
    }
    public static void dessinerPreview(Canvas canvas, Paint pinceau, Point centre, Point pendant) {
        int rayon = 0;
        if (pendant != null) {
            rayon = (int) Math.round(Math.sqrt(Math.pow(pendant.x - centre.x, 2) + Math.pow(pendant.y - centre.y, 2)));
        }
        canvas.drawCircle(centre.x, centre.y, rayon, pinceau);
    }
}
