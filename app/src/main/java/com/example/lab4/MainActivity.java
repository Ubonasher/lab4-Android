package com.example.lab4;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView aX;
    TextView aY;
    TextView aZ;
    TextView mX;
    TextView mY;
    TextView mZ;
    TextView proximity;
    TextView light;
    SensorManager sensorManager;
    Sensor aSensor;
    Sensor pSensor;
    Sensor mSensor;
    Sensor lSensor;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        aX = (TextView) findViewById(R.id.aX);
        aY = (TextView) findViewById(R.id.aY);
        aZ = (TextView) findViewById(R.id.aZ);
        mX = (TextView) findViewById(R.id.mX);
        mY = (TextView) findViewById(R.id.mY);
        mZ = (TextView) findViewById(R.id.mZ);
        proximity = (TextView) findViewById(R.id.proximity);
        light = (TextView) findViewById(R.id.light);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        pSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    }
    public void GoToIZ(View view){
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            aX.setText(Float.toString(event.values[0]));
            aY.setText(Float.toString(event.values[1]));
            aZ.setText(Float.toString(event.values[2]));
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mX.setText(Float.toString(event.values[0]));
            mY.setText(Float.toString(event.values[1]));
            mZ.setText(Float.toString(event.values[2]));
        }
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proximity.setText(Float.toString(event.values[0]));
        }
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            light.setText(Float.toString(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onStart() {
        super.onStart();
        sensorManager.registerListener(this, aSensor, sensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, mSensor, sensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, pSensor, sensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, lSensor, sensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this, aSensor);
        sensorManager.unregisterListener(this, mSensor);
        sensorManager.unregisterListener(this, pSensor);
        sensorManager.unregisterListener(this, lSensor);
    }
}
