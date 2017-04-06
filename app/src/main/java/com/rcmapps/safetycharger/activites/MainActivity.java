package com.rcmapps.safetycharger.activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.fragments.PasswordChangeDialogFragment;
import com.rcmapps.safetycharger.interfaces.MainView;
import com.rcmapps.safetycharger.listeners.AlarmStateListener;
import com.rcmapps.safetycharger.listeners.ButtonClickListener;
import com.rcmapps.safetycharger.presenters.MainPresenter;
import com.rcmapps.safetycharger.services.SafetyAlarmService;
import com.rcmapps.safetycharger.utils.PreferenceContants;
import com.rcmapps.safetycharger.utils.SharedPreferenceUtils;
import com.rcmapps.safetycharger.utils.UtilMethods;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.enableAlarmCB)
    CheckBox enableAlarmCB;
    @BindView(R.id.setPasswordBtn)
    Button setPasswrodBtn;
    @BindView(R.id.chooseAlarmToneBtn)
    Button chooseAlarmBtn;


    private MainPresenter presenter;
    private PasswordChangeDialogFragment passwordChangeDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);
        presenter.init();
    }

    @Override
    public void initView() {

        enableAlarmCB.setChecked(sharedPreferenceUtils.getBoolean(PreferenceContants.KEY_IS_SERVICE_RUNNING, false));

    }

    @Override
    public void defineClickListener() {

        enableAlarmCB.setOnCheckedChangeListener(new AlarmStateListener(presenter));
        chooseAlarmBtn.setOnClickListener(new ButtonClickListener(presenter, sharedPreferenceUtils));
        setPasswrodBtn.setOnClickListener(new ButtonClickListener(presenter, sharedPreferenceUtils));
    }

    @Override
    public void showPasswordChangeDialog(String prevPassword) {

        passwordChangeDialogFragment = new PasswordChangeDialogFragment(presenter);
        passwordChangeDialogFragment.show(getSupportFragmentManager(), PasswordChangeDialogFragment.class.getSimpleName());
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
        Intent intent = new Intent(this, SafetyAlarmService.class);
        startService(intent);
        sharedPreferenceUtils.putBoolean(PreferenceContants.KEY_IS_SERVICE_RUNNING, true);
    }

    @Override
    public void stopSafetyAlarm() {
        Intent intent = new Intent(this, SafetyAlarmService.class);
        stopService(intent);
        sharedPreferenceUtils.putBoolean(PreferenceContants.KEY_IS_SERVICE_RUNNING, false);
    }

    @Override
    public boolean isPasswordSet() {
        return sharedPreferenceUtils.getString(PreferenceContants.KEY_PASSWORD, "").length() > 0;
    }

    @Override
    public void uncheckAlarmSwitch() {
        enableAlarmCB.setChecked(false);
    }


}
