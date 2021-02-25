package com.example.sharikisurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.Random;

public class Ball extends GeometricObject{
    protected int radius;
    //protected int x, y, dx, dy, dx1, dy1, dx2, dy2;
    protected int[] colors = {R.color.red, R.color.orange, R.color.yellow, R.color.green, R.color.blue, R.color.dark_blue, R.color.purple};
    protected int currentColor;
    protected float screenWidth, screenHeight; //screenResolution //
    protected int moveSpeed; //
    Random random = new Random();
        /*
        float xToY;
        Random r = new Random();
        Paint p = new Paint();
        protected Point point;
         */

    public Ball(int radius, int x, int y, Context context) {
        this.radius = radius;
        this.width = 2 * radius;
        this.heigth = 2 * radius;
        this.x = x;
        this.y = y;
        dx = random.nextInt(10) * 10 - 50;
        dy = random.nextInt(10) * 10 - 50;
        currentColor = random.nextInt(colors.length);
        currentColorId = colors[currentColor];

        //currentColor = colors[ThreadLocalRandom.current().nextInt(1, colors.length)]; // по документации лучше использовать при работе с потоками
        moveSpeed = (int) random.nextFloat() * 20 - 5; //
        paint.setColor(getColor(context));
    }


    @Override //
    public float getTop() {return y - radius;}
    @Override
    public float getBot() {return y + radius;}
    @Override
    public float getLeft() {return x - radius;}
    @Override
    public float getRight() {return x + radius;}

    @Override
    protected boolean isCollidedWithObjectHorizontal(GeometricObject g1) {
        if (g1 instanceof Ball) {
            return Math.sqrt(Math.pow(g1.x - x - dx + g1.dx, 2) + Math.pow(g1.y - y - dy + g1.dy, 2)) <= (((Ball) g1).radius + radius)
                    && (x + radius < g1.x || x - radius > g1.x);
        }
        return super.isCollidedWithObjectHorizontal(g1);
    }

    @Override
    protected boolean isCollidedWithObjectVertical(GeometricObject g1) {
        if (g1 instanceof Ball) {
            return Math.sqrt(Math.pow(g1.x - x - dx + g1.dx, 2) + Math.pow(g1.y - y - dy + g1.dy, 2)) <= (((Ball) g1).radius + radius)
                    && (y + radius < g1.y || y - radius > g1.y);
        }
        return super.isCollidedWithObjectVertical(g1);
    }

    public void nextColor() {
        currentColor = ++currentColor % colors.length;
        //paint.setColor(colors[currentColor]);
        currentColorId = colors[currentColor];
    }
    public int getColorIndex() {
        return currentColor;
    }
    public void move(BallsSurfaceView map){
        boolean isChangedX = false, isChangedY = false;

        if(isCollideWithHorizontalBorder(map)) {
            nextColor();
            dx = -dx;
            isChangedX = true;
        }
        if(isCollideWithVerticalBorder(map)) {
            nextColor();
            dy = -dy;
            isChangedY = true;
        }
        for (GeometricObject obj : map.objects) {
            if (!isChangedX && !obj.equals(this) && isCollidedWithObjectHorizontal(obj)) {
                dx = -dx;
            }
            if (!isChangedY && !obj.equals(this) && isCollidedWithObjectVertical(obj)) {
                dy = -dy;
            }
        }
        x += dx;
        y += dy;
    }

    @Override
    public void draw(Canvas c, Context context) {
        paint.setColor(getColor(context));
        c.drawCircle(x, y, radius, paint);
        //c.drawRect(float get);
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

}