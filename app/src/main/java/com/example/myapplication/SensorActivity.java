package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private Sensor sPressure, sLight, sTemperature;

    TextView light_tv, pressure_tv, temperature_tv, longitude_tv, latitude_tv;

    private static final String FILE_NAME = "INF8405.txt";

    Button saveInfo_bt, getInfo_bt;

    private double latitude, longitude, pressure, light, temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        /***
         * Recuperer les informations de MapsActivity
         * Longitude et latitude
         */
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("latitude")){
                latitude = (double) intent.getDoubleExtra("latitude", 0);
            }

            if(intent.hasExtra("longitude")){
                longitude = (double) intent.getDoubleExtra("longitude", 0);
            }
        }

        light_tv = (TextView)findViewById(R.id.light);
        pressure_tv = (TextView)findViewById(R.id.pressure);
        temperature_tv = (TextView)findViewById(R.id.temperature);

        longitude_tv = (TextView)findViewById(R.id.longitude);
        longitude_tv.setText("Longitude : " + longitude);

        latitude_tv = (TextView)findViewById(R.id.latitude);
        latitude_tv.setText("Latitude : " + latitude);

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
            temperature = 0;
        }

        sPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if(sPressure == null){
            pressure_tv.setText("La pression n'est pas supportée");
            pressure = 0;
        }

        sLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(sLight == null){
            light_tv.setText("La luminosité n'est pas supportée");
            light = 0;
        }

        /***
         * sauvegarder les informations de notre position dans un fichier local
         */
        saveInfo_bt = (Button)findViewById(R.id.save_informations);
        saveInfo_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInformation();
            }
        });

        getInfo_bt = (Button) findViewById(R.id.get_informations);
        getInfo_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformation();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if(sensor.getType() == Sensor.TYPE_PRESSURE) {
            pressure_tv.setText("La pression est : " + event.values[0]);
            pressure = event.values[0];
        } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
            light_tv.setText("La luminosité est : " + event.values[0]);
            light = event.values[0];
        } else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            temperature_tv.setText("La temperature est : " + event.values[0]);
            temperature = event.values[0];
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

    public void saveInformation(){
        FileOutputStream fos = null;

        DataUser dataUser = new DataUser(latitude, longitude, pressure, light, temperature);

        boolean isPresent = false;

        if(Utils.dataList.size() == 0){
            Utils.dataList.add(dataUser);
        } else {
            for (DataUser data : Utils.dataList){
                if (data.getLatitude() == dataUser.getLatitude() && data.getLongitude() == dataUser.getLongitude()){
                    isPresent = true;
                }
            }

            if(!isPresent){
                Utils.dataList.add(dataUser);
            }
        }

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(String.valueOf(dataUser).getBytes());

            Toast.makeText(getApplicationContext(), "Enregistrée dans le fichier " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getInformation(){
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            Toast.makeText(getApplicationContext(), "chemin des données " + sb.toString(),
                    Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Intent intent = new Intent(getApplicationContext(), PositionActivity.class);
        startActivity(intent);
    }
}
