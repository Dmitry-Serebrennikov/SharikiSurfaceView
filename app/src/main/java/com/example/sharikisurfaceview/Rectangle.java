package com.example.sharikisurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Rectangle extends GeometricObject {

    public Rectangle(int x, int y, int width, int height, int color, Context context){
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = height;
        this.currentColorId = color;
        paint = new Paint();
        paint.setColor(getColor(context));
    }

    @Override
    public void move(BallsSurfaceView map) { }

    @Override
    public void draw(Canvas c, Context context) {
        c.drawRect(x, y, x + width, y + heigth, paint);
    }
}
