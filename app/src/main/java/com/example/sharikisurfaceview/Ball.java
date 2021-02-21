package com.example.sharikisurfaceview;

import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.Random;

public class Ball extends GeometricObject{
    protected float radius;
    protected float x, y, dx, dy, dx1, dy1, dx2, dy2;
    protected final int[] colors = {R.color.red, R.color.orange, R.color.yellow, R.color.green, R.color.blue, R.color.dark_blue, R.color.purple};
    protected int currentColor;
    protected float screenWidth, screenHeight; //screenResolution
    protected int moveSpeed;
    Random random = new Random();
        /*
        int moveSpeed;
        float xToY;
        Random r = new Random();
        Paint p = new Paint();
         */

    //protected Point point;

    public Ball(float radius, float x, float y) { //int coordX, int coordY
        //point = new Point(coordX, coordY);
        this.radius = radius;
        this.x = x;
        this.y = y;
        dx = 50;
        dy = 50;
        currentColor = colors[random.nextInt(colors.length)];
        //currentColor = colors[ThreadLocalRandom.current().nextInt(1, colors.length)]; // по документации лучше использовать при работе с потоками
        moveSpeed = (int) random.nextFloat() * 20 - 5;
        paint.setColor(colors[random.nextInt(colors.length)]);
    }

    public int nextColor() {
        currentColor = ++currentColor % colors.length;
        return colors[currentColor];
    }

    public void move(BallsSurfaceView map){
        if(isCollideWithHorizontalBorder(map)) {
            dx = -dx;
        }
        if(isCollideWithVerticalBorder(map)) {
            dy = -dy;
        }
        x += dx;
        y += dy;
    }

    @Override
    public void draw(Canvas c) {
        c.drawCircle(x, y, radius, paint);
    }
    // 1 2 3 4 5 6 7
    //движение

    //столкновение со стенкой

    //столкновение с квадратом

    //столкновение двух шаров


    // getX getY getSpeed etc
    public float getRadius() {
        return radius;
    }

    @Override
    protected int getColor() {
        return 0;
    }
}