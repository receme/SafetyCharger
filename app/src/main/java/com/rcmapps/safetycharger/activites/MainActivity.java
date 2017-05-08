package com.rcmapps.safetycharger.activites;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.github.zagum.switchicon.SwitchIconView;
import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.fragments.EnterPasswordDialogFragment;
import com.rcmapps.safetycharger.inappbilling.BillingCallback;
import com.rcmapps.safetycharger.inappbilling.InappBillingManager;
import com.rcmapps.safetycharger.interfaces.MainView;
import com.rcmapps.safetycharger.listeners.ButtonClickListener;
import com.rcmapps.safetycharger.presenters.MainPresenter;
import com.rcmapps.safetycharger.services.SafetyAlarmService;
import com.rcmapps.safetycharger.utils.AdmobAdUtils;
import com.rcmapps.safetycharger.utils.InstructionManager;
import com.rcmapps.safetycharger.utils.PreferenceContants;
import com.rcmapps.safetycharger.utils.UtilMethods;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseActivity implements MainView, BillingCallback {

    @BindView(R.id.toggleAlarmBtn)
    SwitchIconView toggleAlarmBtn;
    @BindView(R.id.settingsBtn)
    ImageView settingsBtn;
    @BindView(R.id.chooseAlarmToneBtn)
    ImageView chooseAlarmToneBtn;

    private static final int REQ_CODE_CHOOSE_ALARM_TONE = 0;
    public static boolean isFromInstructionFlow = false;

    private MainPresenter presenter;
    private EnterPasswordDialogFragment enterPasswordDialogFragment = new EnterPasswordDialogFragment();
    private AudioManager mAudioManager;
    private AdmobAdUtils admobAdUtils;
    private InstructionManager instructionManager = new InstructionManager();
    private InappBillingManager billingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        presenter = new MainPresenter(this);
        presenter.init();
        admobAdUtils = AdmobAdUtils.getInstance(this);

        if(!sharedPreferenceUtils.getBoolean(PreferenceContants.KEY_PREMIUM,false)){
            if(UtilMethods.isGooglePlayServicesAvailable(this) && UtilMethods.isInternetAvailable(this)){
                billingManager = new InappBillingManager(this);
                billingManager.setBillingCallback(this);
                billingManager.setup();
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!sharedPreferenceUtils.getBoolean(PreferenceContants.KEY_IS_ALARM_STARTED, false)){
                    admobAdUtils.showAd();
                }
            }
        },4000);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.checkIfAlarmStarted(sharedPreferenceUtils.getBoolean(PreferenceContants.KEY_IS_ALARM_STARTED, false));

        admobAdUtils.startLoadingAd();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        if(isFromInstructionFlow){
            isFromInstructionFlow = false;
            instructionManager.showIntructionOnTapSetAlarmBtn(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public String getActivityTitle() {
        return getString(R.string.app_name);
    }

    @Override
    public int getLayoutId() {
        return R.layout.main;
    }

    @Override
    public void initView() {

        toggleAlarmBtn.setIconEnabled(sharedPreferenceUtils.getBoolean(PreferenceContants.KEY_IS_SERVICE_RUNNING, false));
    }

    @Override
    public void defineClickListener() {

        ButtonClickListener buttonClickListener = new ButtonClickListener(presenter, sharedPreferenceUtils);
        toggleAlarmBtn.setOnClickListener(buttonClickListener);
        chooseAlarmToneBtn.setOnClickListener(buttonClickListener);
        settingsBtn.setOnClickListener(buttonClickListener);
    }

    @Override
    public void showEnterPasswordDialog() {
        if (enterPasswordDialogFragment != null && !enterPasswordDialogFragment.isAdded()) {
            enterPasswordDialogFragment = new EnterPasswordDialogFragment();
            enterPasswordDialogFragment.setPresenter(presenter);
            enterPasswordDialogFragment.show(getSupportFragmentManager(), "EnterPasswordDialogFragment");
        }
    }

    @Override
    public void closeEnterPasswordDialog() {
        if (enterPasswordDialogFragment != null && enterPasswordDialogFragment.isAdded()) {
            enterPasswordDialogFragment.dismissAllowingStateLoss();
        }
    }

    @Override
    public void setSafetyAlarm() {

        if (!UtilMethods.isServiceRunning(this, SafetyAlarmService.class)) {
            UtilMethods.printLog("Starting service");
            Intent intent = new Intent(this, SafetyAlarmService.class);
            startService(intent);
            sharedPreferenceUtils.putBoolean(PreferenceContants.KEY_IS_SERVICE_RUNNING, true);

            //toggleAlarmBtn.setImageResource(R.mipmap.alarm_on);
            toggleAlarmBtn.setIconEnabled(true,true);
        } else {
            UtilMethods.printLog("Service is already running");
        }
    }

    @Override
    public void stopSafetyAlarm() {
        Intent intent = new Intent(this, SafetyAlarmService.class);
        stopService(intent);
        sharedPreferenceUtils.putBoolean(PreferenceContants.KEY_IS_SERVICE_RUNNING, false);
        sharedPreferenceUtils.putBoolean(PreferenceContants.KEY_IS_ALARM_STARTED, false);
        uncheckAlarmSwitch();

        admobAdUtils.showAd();
    }

    @Override
    public boolean isPasswordSet() {
        return sharedPreferenceUtils.getString(PreferenceContants.KEY_PASSWORD, "").length() > 0;
    }

    @Override
    public void uncheckAlarmSwitch() {
        //toggleAlarmBtn.setImageResource(R.mipmap.alarm_off);
        toggleAlarmBtn.setIconEnabled(false,true);
    }

    @Override
    public String getSavedPassword() {
        return sharedPreferenceUtils.getString(PreferenceContants.KEY_PASSWORD, PreferenceContants.KEY_PASSWORD_DEFAULT_VALUE);
    }

    @Override
    public void updateToggleButton(int resource) {
        toggleAlarmBtn.setImageResource(resource);
    }

    @Override
    public void showSettingsScreen() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAlarmChooser() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALL);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select alarm tone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
        startActivityForResult(intent, REQ_CODE_CHOOSE_ALARM_TONE);
    }

    @Override
    public void showInstruction() {
        instructionManager.showIntructionOnTapSettingsBtn(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_CODE_CHOOSE_ALARM_TONE && resultCode == RESULT_OK){
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (uri != null)
            {
                sharedPreferenceUtils.putString(PreferenceContants.KEY_SELECTED_ALARM_TONE_URI,uri.toString());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        presenter.checkCableStatus(event.isCableConnected());
    }

    @Override
    public void onRestorePurchase(boolean mIsPremium) {
        sharedPreferenceUtils.putBoolean(PreferenceContants.KEY_PREMIUM,mIsPremium);
    }

    @Override
    public void onPurchaseSuccess() {

    }

    @Override
    public void onPurchaseFailure(String message) {

    }

    public static class MessageEvent {
        private boolean cableConnected;

        public MessageEvent(boolean cableConnected) {
            this.cableConnected = cableConnected;
        }

        public boolean isCableConnected() {
            return cableConnected;
        }
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        super.onKeyLongPress(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Log.w("onKeyLongPress", "I WORK BRO.");
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_PLAY_SOUND);
            return false;
        }
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Log.w("onKeyDown", "I WORK BRO.");
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_PLAY_SOUND);
            return false;
        }
        return true;
    }
}
