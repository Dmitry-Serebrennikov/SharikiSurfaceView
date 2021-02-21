package com.example.sharikisurfaceview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

import java.util.Random;

public abstract class GeometricObject {
    protected float x, y, width, heigth;

    protected abstract int getColor();

    Random random = new Random();
    Paint paint = new Paint();

    protected boolean isCollidedWithObject(GeometricObject g1) {
         return (getLeft() < g1.getRight() && getRight() > g1.getLeft() &&
                 getTop() < g1.getBot() && getBot() > g1.getTop());
    }
    protected boolean isCollideWithHorizontalBorder(SurfaceView map) {
        return getLeft() <= 0 || getRight() >= map.getWidth();
    }
    protected boolean isCollideWithVerticalBorder(SurfaceView map) {
        return getTop() <= 0 || getBot() >= map.getHeight();
    }
    public abstract void move(BallsSurfaceView map);
    public float getTop() {return y;}
    public float getBot() {return y + heigth;}
    public float getLeft() {return x;}
    public float getRight() {return x + width;}

    public abstract void draw(Canvas c);
}
