package com.example.sharikisurfaceview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

import java.util.Random;

public abstract class GeometricObject {
    protected int x, y, width, heigth, dx, dy;

    protected abstract int getColor();

    Random random = new Random();
    Paint paint = new Paint();

    protected boolean isCollidedWithObjectHorizontal(GeometricObject g1) {
         return getLeft() < g1.getRight() && getRight() > g1.getLeft();
    }
    protected boolean isCollidedWithObjectVertical(GeometricObject g1) {
        return getTop() < g1.getBot() && getBot() > g1.getTop();
    }
    protected boolean isCollideWithHorizontalBorder(SurfaceView map) {
        return getLeft() <= Math.abs(dx) || getRight() >= map.getWidth() - Math.abs(dx);
    }
    protected boolean isCollideWithVerticalBorder(SurfaceView map) {
        return getTop() <= Math.abs(dy) || getBot() >= map.getHeight() - Math.abs(dy);
    }
    public abstract void move(BallsSurfaceView map);
    public float getTop() {return y;}
    public float getBot() {return y + heigth;}
    public float getLeft() {return x;}
    public float getRight() {return x + width;}

    public abstract void draw(Canvas c);
}
