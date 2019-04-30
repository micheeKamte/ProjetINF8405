package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private TextView appName, devName_1, devName_2, devName_3, appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appName = findViewById(R.id.tv_applicationName);
        appName.setText("Utilitaire App.");

        devName_1 = findViewById(R.id.devName1);
        devName_1.setText("Benjamin SONKWE");

        devName_2 = findViewById(R.id.devName2);
        devName_2.setText("Staelle MASSADO");

        devName_3 = findViewById(R.id.devName3);
        devName_3.setText("Michée KAMTE");

        appVersion = findViewById(R.id.tv_versionApp);
        appVersion.setText(" version 1.0");

        /***
         * Création de l'activité pour le SplashScreen
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Presentation.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
