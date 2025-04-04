package com.CaronCorp.tp1;

import android.gesture.GestureStroke;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Rectangle extends Forme{
    private Point depart;
    private Point arrive;

    public Rectangle(String couleur, int strokeWidth, Point depart, Point arrive) {
        super(couleur, strokeWidth);
        this.depart = new Point(depart);
        this.arrive = new Point(arrive);
    }


    @Override
    public void dessiner(Canvas canvas, Paint pinceau, String background) {
        configurerPinceau(pinceau, background);
        canvas.drawRect(depart.x, depart.y, arrive.x, arrive.y, pinceau);
    }



    @Override
    public void dessinerPreview(Canvas canvas, Paint pinceau) {
        canvas.drawRect(depart.x, depart.y, arrive.x, arrive.y, pinceau);
    }



}
