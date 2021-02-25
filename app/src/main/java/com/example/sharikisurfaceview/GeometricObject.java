package com.example.sharikisurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;

import java.util.Random;

public abstract class GeometricObject {
    protected int x, y, width, heigth, dx, dy;

    protected int currentColorId;
    protected int getColor(Context context){
        return ContextCompat.getColor(context, currentColorId);
    };

    Random random = new Random();
    Paint paint = new Paint();

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

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

    public abstract void draw(Canvas c, Context context);
}
