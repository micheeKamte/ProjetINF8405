package com.example.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class CompassSensor implements SensorEventListener {
    private Context context;
    private ImageView internal;
    private TextView value;
    private SensorManager sensorManager;
    private float currentDegree = 0.f;
    private Sensor mMagnetometer;

    public CompassSensor(Context context, final ImageView internal, final TextView value, float currentDegree) {
        this.context = context;
        this.internal = internal;
        this.value = value;
        this.currentDegree = currentDegree;
        this.sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        this.mMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public SensorManager getSensorManager() {
        return sensorManager;
    }

    public Sensor getmMagnetometer() {
        return mMagnetometer;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            float degree= (float) Math.sqrt(Math.pow(event.values[0], 2)+Math.pow(event.values[1], 2)+Math.pow(event.values[2], 2))*-1;
            System.out.println(degree);
            value.setText(new DecimalFormat("#").format(Math.abs(degree))+" µTesla");
            if(degree!=-0.0){
                RotateAnimation rotateAnimation = new RotateAnimation(
                        currentDegree,
                        +degree,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f);

                rotateAnimation.setDuration(210);
                rotateAnimation.setFillAfter(true);

                internal.startAnimation(rotateAnimation);
                currentDegree = -degree;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
