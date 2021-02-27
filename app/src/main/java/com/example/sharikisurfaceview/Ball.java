package com.example.sharikisurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.Random;

public class Ball extends GeometricObject{
    protected int radius;
    protected int oldDx, oldDy;//x, y, dx, dy, dx1, dy1, dx2, dy2;
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
        oldDx = dx;
        oldDy = dy;

        //currentColor = colors[ThreadLocalRandom.current().nextInt(1, colors.length)]; // по документации лучше использовать при работе с потоками
        moveSpeed = (int) random.nextFloat() * 20 - 5; //
        paint.setColor(getColor(context));
    }


    @Override //
    public int getTop() {return (int) Math.round(y -radius*0.5 /*(radius + radius / Math.sqrt(2))/2*/);}
    @Override
    public int getBot() {return (int) Math.round(y + radius*0.5 /*(radius + radius / Math.sqrt(2))/2*/);}
    @Override
    public int getLeft() {return (int) Math.round(x - radius*0.5/*(radius + radius / Math.sqrt(2))/2*/);}
    @Override
    public int getRight() {return (int) Math.round(x + radius*0.5 /*(radius + radius / Math.sqrt(2))/2*/);}

    public boolean isBallsColliding(Ball ball)
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
/*

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
*/

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
        oldDx = dx;
        oldDy = dy;
        for (GeometricObject obj : map.objects) {
            if(obj.equals(this))
                continue;
            if (obj instanceof Ball && isBallsColliding((Ball) obj)) {
                ballsCollide((Ball) obj);
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