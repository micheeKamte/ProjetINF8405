package com.example.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private Sensor sPressure, sLight, sTemperature;

    TextView light_tv, pressure_tv, temperature_tv, longitude_tv, latitude_tv;

    Button saveInfo_bt, getInfo_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        light_tv = (TextView)findViewById(R.id.light);
        pressure_tv = (TextView)findViewById(R.id.pressure);
        temperature_tv = (TextView)findViewById(R.id.temperature);

        longitude_tv = (TextView)findViewById(R.id.longitude);
        longitude_tv.setText("Longitude : ...");

        latitude_tv = (TextView)findViewById(R.id.latitude);
        latitude_tv.setText("Latitude : ...");

        /***
         * Obtenir une instance du service de capteur, et l'utiliser pour obtenir
         * des instances des differents capteurs
         */
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        /***
         * Obtenir les instances des capteurs désirées
         */
        sTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(sTemperature == null){
            temperature_tv.setText("La température n'est pas supportée");
        }

        sPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if(sPressure == null){
            pressure_tv.setText("La pression n'est pas supportée");
        }

        sLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(sLight == null){
            light_tv.setText("La luminosité n'est pas supportée");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if(sensor.getType() == Sensor.TYPE_PRESSURE) {
            pressure_tv.setText("La pression est : " + event.values[0]);
        } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
            light_tv.setText("La luminosité est : " + event.values[0]);
        } else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            temperature_tv.setText("La temperature est : " + event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        /***
         * Arreter l'activité du capteur quand l'activité est en pause.
         * eviter la surconsommation de la batterie de l'appareil
         */
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        /***
         * enregister les valeurs des capteurs
         */
        super.onResume();
        if(sTemperature != null){
            sensorManager.registerListener(SensorActivity.this, sTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(sPressure != null){
            sensorManager.registerListener(SensorActivity.this, sPressure, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(sLight != null){
            sensorManager.registerListener(SensorActivity.this, sLight, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }
}
