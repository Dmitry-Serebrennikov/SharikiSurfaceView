package com.example.sharikisurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BallsSurfaceView extends SurfaceView implements SurfaceHolder.Callback2 {
    //сайт со звуками - zapsplat
    DrawThread thread;
    //Display display;
    public List<GeometricObject> objects = new ArrayList<GeometricObject>();

    public BallsSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);//получить холдер и повесить на него обработчик


    }
    public void createBall() {
        Random random = new Random();
        int randomRadius = random.nextInt(10) * 10 + 50;
        int randomX = random.nextInt((getWidth() - 3 * randomRadius) / 10) * 10 + randomRadius;
        int randomY = random.nextInt((getHeight() - 3 * randomRadius) / 10) * 10 + randomRadius;
        objects.add(new Ball(randomRadius, randomX, randomY));
    }
    class DrawThread extends Thread {
        //float x = 700, y = 600, dx1, dy1, dx2, dy2;
        //Random r = new Random();
        //Paint p = new Paint();
        boolean runFlag = true;


        // в конструкторе нужно передать holder для дальнейшего доступа к канве
        public DrawThread(SurfaceHolder holder, BallsSurfaceView view){
            this.holder = holder;
            this.view = view;
        }
        SurfaceHolder holder;
        BallsSurfaceView view;
        @Override
        public void run() { //был onDraw -> получаем по-другому -- пока происходит отрисовка кадров на поверхности, запущен метод run
            super.run();

            //выполняем цикл пока (рисуем кадры) включен флаг
            synchronized (objects) {
                if (objects.size() < 2) {
                    view.createBall();
                    view.createBall();
                }
            }
            while (runFlag){
                Canvas c = holder.lockCanvas();//фиксируем канву //получаем управление над холстом
                if (c != null) {
                    c.drawColor(Color.WHITE);
                    for (GeometricObject object : objects) { //почему не просто objects?
                        object.move(view);
                        object.draw(c);
                    }

                    //Log.d("mytag", objects.size() + " rhpoherg");
                    holder.unlockCanvasAndPost(c);
                    try {
                        Thread.sleep(50); }
                    catch (InterruptedException e) {}
                }



                //если успешно захватили канву
                /*if (c != null) {
                    c.drawColor(Color.RED);

                    //случайные блуждения - сдвигаем координаты шарика в случайные стороны
                    x += r.nextFloat() * 20 - 5; // меняем координаты
                    y += r.nextFloat() * 20 - 5;
                    c.drawCircle(x, y, 100, p);// рисуем круг
                    holder.unlockCanvasAndPost(c); // нарисовали и отпускаем канву
                    *//*
                    *   Для начала сделайте движущийся шарик, который при ударении о стенки меняет цвет. Достаточно двигаться по одной координате
                        Второй шаг: два шарика и движение по всему полю в случайном (изначально) направлении
                      * набор цветов хранить в ресурсах
                      * параметры хранить в массиве
                      * шарики меняют цвет при столкновении со стенками
                      * шарик может двигаться по вертикали/горизонтали
                      *
                      * 2 шарики, создаются в разных местах
                      * удар о стенку - меняются просто направлении при столкновении
                      * при горизонтали - dx
                      * при вертикали - dy
                      *
                      * столкновение шаров по измерению их радиусов
                    * *//*
                    // нужна пауза на каждом кадре
                    try {
                        Thread.sleep(100); }
                    catch (InterruptedException e) {}
                }*/

            }
        }
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        //
        return true;
    }*/

    @Override
    public void surfaceRedrawNeeded(@NonNull SurfaceHolder holder) {}

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread = new DrawThread(holder, this); //перенесли
        thread.start();

        Log.d("mytag", "DrawThread is running");
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        //при изменении конфигурации поверхности поток нужно перезапустить
        thread.runFlag = false;
        thread = new DrawThread(holder, this);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        thread.runFlag = false;
    }
}
