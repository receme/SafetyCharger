package com.rcmapps.safetycharger.services;


import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

public class VolumeObserver extends ContentObserver {

    //private AudioManager mAudioManager;

    public VolumeObserver(Context context,Handler handler) {
        super(handler);
        //mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

    }

    @Override
    public boolean deliverSelfNotifications() {
        return false;
    }

    @Override
    public void onChange(boolean selfChange) {
//        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM);
//        UtilMethods.printLog("CurrentVol: "+currentVolume);
//        mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM), AudioManager.FLAG_PLAY_SOUND);
    }
}
