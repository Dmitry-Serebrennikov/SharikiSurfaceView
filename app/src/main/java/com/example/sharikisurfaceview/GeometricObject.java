package com.example.sharikisurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;

public abstract class GeometricObject {
    protected int x, y, width, heigth, dx, dy;

    protected int currentColorId;

    protected int getColor(Context context) {
        return ContextCompat.getColor(context, currentColorId);
    }

    Paint paint = new Paint();

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    protected boolean isCollidedWithObjectHorizontal(GeometricObject g1) {

        return (((getRight() + dx) >= g1.getLeft() && (getLeft() + dx) < g1.getLeft())
                || (getLeft() + dx) <= g1.getRight() && (getRight() + dx) > g1.getRight())
                && (getBot() > g1.getTop() && getTop() < g1.getBot());
    }

    protected boolean isCollidedWithObjectVertical(GeometricObject g1) {
        return ((getBot() + dy) >= g1.getTop() && (getTop() + dy) < g1.getTop()
                || (getTop() + dy) <= g1.getBot() && getBot() + dy > g1.getBot())
                && (getRight() + dx >= g1.getLeft() && getLeft() + dx <= g1.getRight());
    }

    protected boolean isCollideWithHorizontalBorder(SurfaceView map) {
        return getLeft() <= Math.abs(dx) || getRight() >= map.getWidth() - Math.abs(dx);
    }

    protected boolean isCollideWithVerticalBorder(SurfaceView map) {
        return getTop() <= Math.abs(dy) || getBot() >= map.getHeight() - Math.abs(dy);
    }

    public abstract void move(BallsSurfaceView map);

    public int getTop() {
        return y;
    }

    public int getBot() {
        return y + heigth;
    }

    public int getLeft() {
        return x;
    }

    public int getRight() {
        return x + width;
    }

    public abstract void draw(Canvas c, Context context);
}
