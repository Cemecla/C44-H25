package com.CaronCorp.tp1;

import android.gesture.GestureStroke;
import android.graphics.Point;

public class Rectangle extends Forme{
    private Point depart;
    private Point arrive;

    public Rectangle(String couleur, int strokeWidth, boolean stroke, Point depart, Point arrive) {
        super(couleur, strokeWidth, stroke);
        this.depart = new Point(depart);
        this.arrive = new Point(arrive);
    }

    public Point getDepart() {
        return depart;
    }

    public Point getArrive() {
        return arrive;
    }
}
