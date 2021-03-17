package com.example.sharikisurfaceview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BallsSurfaceView extends SurfaceView implements SurfaceHolder.Callback2, View.OnTouchListener {
    //сайт со звуками - zapsplat
    DrawThread thread;
    Activity context;
    SoundPool soundpool;
    int rectWidth = 250, rectHeight = 250;

    public BallsSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        this.context = (Activity) context;
        setOnTouchListener(this);

        int MAX_STREAMS = 2;

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundpool = new SoundPool.Builder()
                .setAudioAttributes(attributes).setMaxStreams(MAX_STREAMS)
                .build();

        soundpool.load(context, R.raw.sound_balls_collision, 1);
        soundpool.load(context, R.raw.sound_collison_with_border,  1);
    }

    public SoundPool getSoundpool() {
        return soundpool;
    }

    public DrawThread getThread() {
        return thread;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (thread.runFlag) {
            for (GeometricObject obj : thread.objects) {
                if (obj instanceof Rectangle){
                    obj.setX((int) (event.getX() - rectWidth / 2));
                    obj.setY((int) (event.getY() - rectHeight / 2));
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            thread = new DrawThread(getHolder(), this);
            thread.start();
        }
        return true;
    }

    class DrawThread extends Thread {
        boolean runFlag = true;
        public List<GeometricObject> objects = new ArrayList<GeometricObject>();
        SurfaceHolder holder;
        BallsSurfaceView view;

        public DrawThread(SurfaceHolder holder, BallsSurfaceView view){
            this.holder = holder;
            this.view = view;
        }

        public Ball createBall() {
            Random random = new Random();
            int randomRadius = random.nextInt(10) * 10 + 50;
            int randomX = random.nextInt((getWidth() - 3 * randomRadius) / 10) * 10 + randomRadius;
            int randomY = random.nextInt((getHeight() - 3 * randomRadius) / 10) * 10 + randomRadius;
            Ball ball = new Ball(randomRadius, randomX, randomY, context);
            for (GeometricObject obj : objects) {
                if (!ball.equals(obj) && (ball.isCollidedWithObjectHorizontal(obj) || ball.isCollidedWithObjectVertical(obj))) {
                    ball = createBall();
                    return ball;
                }
            }
            objects.add(ball);

            return ball;
        }

        public void createRectangle() {
            int x = getWidth() / 2 - rectWidth / 2;
            int y = getHeight() / 2 - rectHeight / 2;
            objects.add(new Rectangle(x, y, rectWidth, rectHeight, R.color.rect_gray, context));
        }


        @Override
        public void run() {
            super.run();

            synchronized (objects) {
                if (objects.size() == 0) {
                    createRectangle();
                    for (int i = 0; i < 2; i++) {
                        createBall();
                    }
                }
            }
            while (runFlag){
                Canvas c = holder.lockCanvas();
                if (c != null) {
                    c.drawColor(Color.WHITE);
                    for (GeometricObject object : objects) {
                        object.move(view);
                        object.draw(c, context);
                    }
                    int currIndex = -1;
                    runFlag = false;
                    for (GeometricObject object : objects) {
                        if (object instanceof Ball) {
                            int colorIndex = ((Ball) object).getColorIndex();
                            if (currIndex < 0) {
                                currIndex = colorIndex;
                                continue;
                            }
                            else if (currIndex != colorIndex) {
                                runFlag = true;
                            }
                        }
                    }
                    if (!runFlag) {
                        context.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(context, "Game over!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    //Log.d("mytag", objects.size() + " objects");
                    holder.unlockCanvasAndPost(c);
                    try {
                        Thread.sleep(50); }
                    catch (InterruptedException e) {}
                }

            }


        }
    }

    @Override
    public void surfaceRedrawNeeded(@NonNull SurfaceHolder holder) {}

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread = new DrawThread(holder, this);
        thread.start();

        //Log.d("mytag", "DrawThread is running");
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        thread.runFlag = false;
        thread = new DrawThread(holder, this);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        thread.runFlag = false;
    }
}
