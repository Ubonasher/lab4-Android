package com.example.lab4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Main2Activity extends Activity implements SensorEventListener {
    Display display;
    Point size;
    int MaxWidth;
    int MaxHeight;
    int MinWidth = 30;
    int MinHeight = 30;
    Paint p;
    Sensor aSensor;
    int aX;
    int aY;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            aX = Integer.valueOf((int) event.values[0]);
            aY = Integer.valueOf((int) event.values[1]);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
    @Override
    public void onStart() {
        super.onStart();
        sensorManager.registerListener(this, aSensor, sensorManager.SENSOR_DELAY_FASTEST);
    }
    @Override
    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this, aSensor);
    }
    class DrawView extends SurfaceView implements SurfaceHolder.Callback {
        private DrawThread drawThread;

        public DrawView(Context context) {
            super(context);
            getHolder().addCallback(this);
            p = new Paint();
        }
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            drawThread = new DrawThread(getHolder());
            drawThread.setRunning(true);
            drawThread.start();
            MaxWidth = this.getMeasuredWidth() - 30;
            MaxHeight = this.getMeasuredHeight() - 30;
            p = new Paint();
        }
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean retry = true;
            drawThread.setRunning(false);
            while (retry) {
                try {
                    drawThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
        }
        class DrawThread extends Thread {
            private boolean running = false;
            private SurfaceHolder surfaceHolder;
            public DrawThread(SurfaceHolder surfaceHolder) {
                this.surfaceHolder = surfaceHolder;
            }
            public void setRunning(boolean running) {
                this.running = running;
            }
            @Override
            public void run() {
                Canvas canvas;
                while (running) {
                    canvas = null;
                    try {
                        canvas = surfaceHolder.lockCanvas(null);
                        if (canvas == null)
                            continue;
                        // заливка канвы цветом
                        canvas.drawARGB(80, 102, 204, 255);
                        // настройка кисти
                        // красный цвет
                        p.setColor(Color.RED);
                        // толщина линии = 10
                        p.setStrokeWidth(10);
                        // рисуем круг с центром в (x,y), радиус = 30
                        canvas.drawCircle(MaxWidth / 2 + aX * -1 * MaxWidth / 30, MaxHeight / 2 + aY * MaxHeight / 30, 30, p);
                    } finally {
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        }

    }
}
