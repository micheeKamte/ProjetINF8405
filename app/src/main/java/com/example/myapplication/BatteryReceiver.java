package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.TextView;

public class BatteryReceiver extends BroadcastReceiver {

    private static final int DEFAULT_VALUE= -1000;
    private static int startupBatteryLevel = DEFAULT_VALUE;
    private int level, scale, currentBatteryLevel;


    public int getCurrentPercentage(Intent intent){
        // Percentage
        level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        currentBatteryLevel  = level * 100 / scale;
        return  currentBatteryLevel;
    }

    public int getStartupBatteryLevel(){
        return startupBatteryLevel;
    }

    public void setStartupBatteryLevel(int batteryLevel){
        startupBatteryLevel = batteryLevel;
    }

    public int getBatteryDelta(int batteryLevel,int percentage){
        return batteryLevel - percentage;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        TextView statusLabel = ((BatteryActivity)context).findViewById(R.id.statusLabel);
        TextView percentageLabel = ((BatteryActivity)context).findViewById(R.id.percentageLabel);
        TextView consumptionLabel = ((BatteryActivity)context).findViewById(R.id.consumptionLabel);


        String action = intent.getAction();
        if (action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {

            // Status
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            String message = "";

            switch (status) {
                case BatteryManager.BATTERY_STATUS_FULL:
                    message = "Full";
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    message = "Charging";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    message = "Discharging";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    message = "Not charging";
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    message = "Unknown";
                    break;
            }

            statusLabel.setText("Statut: " + message);
            percentageLabel.setText("Niveau actuel: " + getCurrentPercentage(intent)  + "%");

            if (getStartupBatteryLevel() == DEFAULT_VALUE)
                setStartupBatteryLevel(getCurrentPercentage(intent));

            int consummationBattery = getBatteryDelta(startupBatteryLevel, getCurrentPercentage(intent));

            if (message == "Charging")
                consummationBattery = 0;

            consumptionLabel.setText("Consommation: " + consummationBattery + "%");
        }
    }

}
