package com.example.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Presentation extends AppCompatActivity {

    private boolean isFABOpen;
    FloatingActionButton fab, scan, located, magneto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);

        isFABOpen = false;

        scan = findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WifiActivity.class);
                startActivity(intent);
            }
        });

        located = findViewById(R.id.located);
        located.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsViewActivity.class);
                startActivity(intent);
            }
        });

        magneto = findViewById(R.id.magneto);
        magneto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MagnetoActivity.class);
                startActivity(intent);
            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /***
     * dérouler les boutons pour les différentes activités
     */
    private void showFABMenu(){
        isFABOpen = true;
        scan.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        located.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        magneto.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }

    /***
     * cacher les boutons
     */
    private void closeFABMenu(){
        isFABOpen = false;
        scan.animate().translationY(0);
        located.animate().translationY(0);
        magneto.animate().translationY(0);
    }
}
