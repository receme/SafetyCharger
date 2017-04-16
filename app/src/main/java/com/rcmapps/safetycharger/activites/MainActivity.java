package com.rcmapps.safetycharger.activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.fragments.EnterPasswordDialogFragment;
import com.rcmapps.safetycharger.fragments.PasswordChangeDialogFragment;
import com.rcmapps.safetycharger.interfaces.MainView;
import com.rcmapps.safetycharger.listeners.AlarmStateListener;
import com.rcmapps.safetycharger.listeners.ButtonClickListener;
import com.rcmapps.safetycharger.presenters.MainPresenter;
import com.rcmapps.safetycharger.services.SafetyAlarmService;
import com.rcmapps.safetycharger.utils.PreferenceContants;
import com.rcmapps.safetycharger.utils.UtilMethods;

import io.fabric.sdk.android.Fabric;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.toggleAlarmBtn)
    ImageView toggleAlarmBtn;
    @BindView(R.id.settingsBtn)
    ImageView settingsBtn;
    @BindView(R.id.chooseAlarmToneBtn)
    ImageView chooseAlarmToneBtn;


    private MainPresenter presenter;
    private PasswordChangeDialogFragment passwordChangeDialogFragment = new PasswordChangeDialogFragment();
    private EnterPasswordDialogFragment enterPasswordDialogFragment = new EnterPasswordDialogFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);
        presenter.init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.checkIfAlarmStarted(sharedPreferenceUtils.getBoolean(PreferenceContants.KEY_IS_ALARM_STARTED,false));
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void initView() {

        //alarmCb.setChecked(sharedPreferenceUtils.getBoolean(PreferenceContants.KEY_IS_SERVICE_RUNNING, false));
        presenter.setAlarmToggleBtnState(sharedPreferenceUtils.getBoolean(PreferenceContants.KEY_IS_SERVICE_RUNNING, false));

    }

    @Override
    public void defineClickListener() {

//        alarmCb.setOnCheckedChangeListener(new AlarmStateListener(presenter));
//        chooseAlarmBtn.setOnClickListener(new ButtonClickListener(presenter, sharedPreferenceUtils));
//        setPasswrodBtn.setOnClickListener(new ButtonClickListener(presenter, sharedPreferenceUtils));
        toggleAlarmBtn.setOnClickListener(new ButtonClickListener(presenter,sharedPreferenceUtils));
        chooseAlarmToneBtn.setOnClickListener(new ButtonClickListener(presenter,sharedPreferenceUtils));
        settingsBtn.setOnClickListener(new ButtonClickListener(presenter,sharedPreferenceUtils));
    }

    @Override
    public void showPasswordChangeDialog(String prevPassword) {

        passwordChangeDialogFragment = new PasswordChangeDialogFragment();
        passwordChangeDialogFragment.setPresenter(presenter);
        passwordChangeDialogFragment.show(getSupportFragmentManager(), PasswordChangeDialogFragment.class.getSimpleName());
    }

    @Override
    public void showEnterPasswordDialog() {
        if(enterPasswordDialogFragment!=null && !enterPasswordDialogFragment.isAdded()){
            enterPasswordDialogFragment = new EnterPasswordDialogFragment();
            enterPasswordDialogFragment.setPresenter(presenter);
            enterPasswordDialogFragment.show(getSupportFragmentManager(),"EnterPasswordDialogFragment");
        }

    }

    @Override
    public void closeEnterPasswordDialog() {
        if(enterPasswordDialogFragment!=null && enterPasswordDialogFragment.isAdded()){
            enterPasswordDialogFragment.dismiss();
        }
    }

    @Override
    public void saveNewPassword(String newPassword) {
        sharedPreferenceUtils.putString(PreferenceContants.KEY_PASSWORD, newPassword);
    }

    @Override
    public void closePasswordChangeDialog() {

        if (passwordChangeDialogFragment != null) {
            passwordChangeDialogFragment.dismiss();
        }
    }

    @Override
    public void setSafetyAlarm() {

        if(!UtilMethods.isServiceRunning(this,SafetyAlarmService.class)){
            UtilMethods.printLog("Starting service");
            Intent intent = new Intent(this, SafetyAlarmService.class);
            startService(intent);
            sharedPreferenceUtils.putBoolean(PreferenceContants.KEY_IS_SERVICE_RUNNING, true);
        }
        else{
            UtilMethods.printLog("Service is already running");
        }
    }

    @Override
    public void stopSafetyAlarm() {
        Intent intent = new Intent(this, SafetyAlarmService.class);
        stopService(intent);
        sharedPreferenceUtils.putBoolean(PreferenceContants.KEY_IS_SERVICE_RUNNING, false);
        sharedPreferenceUtils.putBoolean(PreferenceContants.KEY_IS_ALARM_STARTED, false);
        //alarmCb.setChecked(false);
        uncheckAlarmSwitch();
    }

    @Override
    public boolean isPasswordSet() {
        return sharedPreferenceUtils.getString(PreferenceContants.KEY_PASSWORD, "").length() > 0;
    }

    @Override
    public void uncheckAlarmSwitch() {
        //alarmCb.setChecked(false);
        toggleAlarmBtn.setImageResource(R.mipmap.alarm_off);
    }

    @Override
    public String getSavedPassword() {
        return sharedPreferenceUtils.getString(PreferenceContants.KEY_PASSWORD,PreferenceContants.KEY_PASSWORD_DEFAULT_VALUE);
    }

    @Override
    public void updateToggleButton(int resource) {
        toggleAlarmBtn.setImageResource(resource);
    }

    @Override
    public void showSettingsScreen() {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        presenter.checkCableStatus(event.isCableConnected());
    }


    public static class MessageEvent {
        private boolean cableConnected;

        public MessageEvent(boolean cableConnected){
            this.cableConnected = cableConnected;
        }

        public boolean isCableConnected() {
            return cableConnected;
        }
    }
}
