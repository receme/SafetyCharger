package com.rcmapps.safetycharger.services;


import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.rcmapps.safetycharger.interfaces.SafetyAlarm;
import com.rcmapps.safetycharger.utils.UtilMethods;

public class SafetyAlarmService extends Service implements SafetyAlarm{

    private PowerConnectionReceiver powerConnectionReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        powerConnectionReceiver = new PowerConnectionReceiver(this);
        registerReceiver(powerConnectionReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (powerConnectionReceiver != null) {
            unregisterReceiver(powerConnectionReceiver);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onPowerCableDisconnected() {
        UtilMethods.printLog("power cable disconnected");
    }
}
