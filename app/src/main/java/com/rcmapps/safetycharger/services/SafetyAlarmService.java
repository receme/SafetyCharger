package com.rcmapps.safetycharger.services;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.activites.MainActivity;
import com.rcmapps.safetycharger.interfaces.SafetyAlarm;
import com.rcmapps.safetycharger.utils.PreferenceContants;
import com.rcmapps.safetycharger.utils.SharedPreferenceUtils;
import com.rcmapps.safetycharger.utils.UtilMethods;

import org.greenrobot.eventbus.EventBus;

public class SafetyAlarmService extends Service implements SafetyAlarm {

    private PowerConnectionReceiver powerConnectionReceiver;

    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        powerConnectionReceiver = new PowerConnectionReceiver(this);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
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
            mediaPlayer = MediaPlayer.create(this, R.raw.sound);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
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
        }
    }
}
