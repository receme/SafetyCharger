package com.rcmapps.safetycharger.services;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.rcmapps.safetycharger.activites.BaseActivity;
import com.rcmapps.safetycharger.activites.MainActivity;
import com.rcmapps.safetycharger.utils.PreferenceContants;
import com.rcmapps.safetycharger.utils.SharedPreferenceUtils;
import com.rcmapps.safetycharger.utils.UtilMethods;

public class PowerConnectionReceiver extends BroadcastReceiver {

    SafetyAlarmService safetyAlarmService;

    public PowerConnectionReceiver(){

    }

    public PowerConnectionReceiver(SafetyAlarmService safetyAlarmService) {
        this.safetyAlarmService = safetyAlarmService;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        if(safetyAlarmService!=null){

            if(usbCharge | acCharge){
                safetyAlarmService.onPowerCableConnected();
            }
            else{
                safetyAlarmService.onPowerCableDisconnected();

                if(BaseActivity.isAppOpen == 0 && SharedPreferenceUtils.getInstance(context).getBoolean(PreferenceContants.KEY_IS_AUTORUN_ON,false)){
                    Intent intent1 = new Intent(context, MainActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                }
            }
        }

    }
}
