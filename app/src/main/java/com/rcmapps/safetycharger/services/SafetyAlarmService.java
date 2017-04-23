package com.rcmapps.safetycharger.services;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.activites.MainActivity;
import com.rcmapps.safetycharger.interfaces.SafetyAlarm;
import com.rcmapps.safetycharger.utils.PreferenceContants;
import com.rcmapps.safetycharger.utils.SharedPreferenceUtils;
import com.rcmapps.safetycharger.utils.UtilMethods;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

public class SafetyAlarmService extends Service implements SafetyAlarm {

    private PowerConnectionReceiver powerConnectionReceiver;

    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        powerConnectionReceiver = new PowerConnectionReceiver(this);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        registerReceiver(powerConnectionReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (powerConnectionReceiver != null) {
            unregisterReceiver(powerConnectionReceiver);
        }

        stopMediaPlayer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onPowerCableDisconnected() {
        UtilMethods.printLog("power cable disconnected");

        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_PLAY_SOUND);
        if (mediaPlayer == null) {

            System.out.println(SharedPreferenceUtils.getInstance(this).getString(PreferenceContants.KEY_SELECTED_ALARM_TONE_URI,""));
            Uri alarmToneUri = Uri.parse(SharedPreferenceUtils.getInstance(this).getString(PreferenceContants.KEY_SELECTED_ALARM_TONE_URI,""));
            if(alarmToneUri!=null){
                mediaPlayer = MediaPlayer.create(this, alarmToneUri);
            }
            else{
                mediaPlayer = MediaPlayer.create(this, R.raw.siren);
            }

            if(mediaPlayer==null){
                return;
            }

            mediaPlayer.setLooping(true);
            mediaPlayer.start();

            if(vibrator!=null && SharedPreferenceUtils.getInstance(this).getBoolean(PreferenceContants.KEY_IS_VIBRATION_ON,false)){
                vibrator.vibrate(new long[]{0,500,1000},0);
            }
        }

        SharedPreferenceUtils.getInstance(this).putBoolean(PreferenceContants.KEY_IS_ALARM_STARTED, true);
        EventBus.getDefault().post(new MainActivity.MessageEvent(false));
    }

    @Override
    public void onPowerCableConnected() {
        stopMediaPlayer();
        SharedPreferenceUtils.getInstance(this).putBoolean(PreferenceContants.KEY_IS_ALARM_STARTED, false);
        UtilMethods.printLog("power cable connected");

        EventBus.getDefault().post(new MainActivity.MessageEvent(true));
    }

    private void stopMediaPlayer() {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;

            if(vibrator!=null){
                vibrator.cancel();
            }

        }
    }
}
