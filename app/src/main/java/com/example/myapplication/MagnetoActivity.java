package com.example.myapplication;

import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MagnetoActivity extends AppCompatActivity {

    private ImageView internal;
    private TextView value;
    private float currentDegree = 0.f;
    private CompassSensor compassSensor;
    private RelativeLayout backg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magneto);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        backg = (RelativeLayout) findViewById(R.id.full);
        backg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        internal = (ImageView)findViewById(R.id.internal);
        value = (TextView) findViewById(R.id.value);

        compassSensor = new CompassSensor(this, internal, value, currentDegree);
    }

    @Override
    protected void onResume() {
        super.onResume();
        compassSensor.getSensorManager().registerListener(compassSensor, compassSensor.getmMagnetometer(),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        compassSensor.getSensorManager().unregisterListener(compassSensor);
    }
}
