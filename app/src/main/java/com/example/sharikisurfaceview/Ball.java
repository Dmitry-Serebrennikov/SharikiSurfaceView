package com.example.sharikisurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.Random;

public class Ball extends GeometricObject{
    protected int radius;
    protected int oldDx, oldDy;
    protected int[] colors = {R.color.red, R.color.orange, R.color.yellow, R.color.green, R.color.blue, R.color.dark_blue, R.color.purple};
    protected int currentColor;
    Random random = new Random();

    public Ball(int radius, int x, int y, Context context) {
        this.radius = radius;
        this.width = 2 * radius;
        this.heigth = 2 * radius;
        this.x = x;
        this.y = y;
        dx = random.nextInt(10) * 10 - 50; // movespeed
        dy = random.nextInt(10) * 10 - 50;
        currentColor = random.nextInt(colors.length);
        currentColorId = colors[currentColor];
        oldDx = dx;
        oldDy = dy;

        //currentColor = colors[ThreadLocalRandom.current().nextInt(1, colors.length)]; // по документации лучше использовать при работе с потоками
        paint.setColor(getColor(context));
    }


    @Override //
    public int getTop() {return (int) Math.round(y -radius*0.75 /*(radius + radius / Math.sqrt(2))/2*/);} // что делать с этим
    @Override
    public int getBot() {return (int) Math.round(y + radius*0.75 /*(radius + radius / Math.sqrt(2))/2*/);}
    @Override
    public int getLeft() {return (int) Math.round(x - radius*0.75/*(radius + radius / Math.sqrt(2))/2*/);}
    @Override
    public int getRight() {return (int) Math.round(x + radius*0.75 /*(radius + radius / Math.sqrt(2))/2*/);}

    public boolean isBallsColliding(Ball ball) //
    {
        int xd = x+oldDx - ball.x-ball.oldDx;
        int yd = y+oldDy - ball.y-ball.oldDy;

        float sumRadius = getRadius() + ball.getRadius();
        float sqrRadius = sumRadius * sumRadius;

        float distSqr = (xd * xd) + (yd * yd);

        return distSqr <= sqrRadius;
    }

    public void ballsCollide(Ball ball){
        dx=ball.oldDx;
        dy=ball.oldDy;
        ball.dx = oldDx;
        ball.dy = oldDy;
    }


    public void nextColor() {
        currentColor = ++currentColor % colors.length;
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
        oldDx = dx;
        oldDy = dy;
        for (GeometricObject obj : map.thread.objects) {
            if(obj.equals(this))
                continue;
            if (obj instanceof Ball && isBallsColliding((Ball) obj)) {
                ballsCollide((Ball) obj);
                map.getSoundpool().play(1, 1, 1, 1, 0, 1);
            }
            else {
                if (!isChangedX && isCollidedWithObjectHorizontal(obj)) {
                    dx = -dx;
                }

                if (!isChangedY && isCollidedWithObjectVertical(obj)) {
                    dy = -dy;
                }
            }
        }
        x += dx;
        y += dy;
    }

    @Override
    public void draw(Canvas c, Context context) {
        paint.setColor(getColor(context));
        c.drawCircle(x, y, radius, paint);
    }

    public float getRadius() {
        return radius;
    }

}