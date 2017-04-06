package com.rcmapps.safetycharger.services;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.interfaces.SafetyAlarm;
import com.rcmapps.safetycharger.utils.PreferenceContants;
import com.rcmapps.safetycharger.utils.SharedPreferenceUtils;
import com.rcmapps.safetycharger.utils.UtilMethods;

public class SafetyAlarmService extends Service implements SafetyAlarm{

    private PowerConnectionReceiver powerConnectionReceiver;

    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    private int userVolume;

    private VolumeObserver volumeObserver;

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

            stopMediaPlayer();
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

        userVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM);

        mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM), AudioManager.FLAG_PLAY_SOUND);
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        //mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        volumeObserver = new VolumeObserver(this,new Handler());
        getApplicationContext().getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, volumeObserver);
        SharedPreferenceUtils.getInstance(this).putBoolean(PreferenceContants.KEY_IS_ALARM_STARTED,true);
    }

    @Override
    public void onPowerCableConnected() {
        stopMediaPlayer();
        mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, userVolume, AudioManager.FLAG_PLAY_SOUND);
        if(volumeObserver!=null){
            getApplicationContext().getContentResolver().unregisterContentObserver(volumeObserver);
        }

    }

    private void stopMediaPlayer(){
        if(mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}
