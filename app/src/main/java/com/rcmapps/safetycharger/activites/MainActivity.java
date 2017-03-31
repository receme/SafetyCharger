package com.rcmapps.safetycharger.activites;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.fragments.PasswordChangeDialogFragment;
import com.rcmapps.safetycharger.interfaces.MainView;
import com.rcmapps.safetycharger.listeners.AlarmStateListener;
import com.rcmapps.safetycharger.listeners.ButtonClickListener;
import com.rcmapps.safetycharger.presenters.MainPresenter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);
        presenter.init();
    }

    @Override
    public void defineClickListener() {

        enableAlarmCB.setOnCheckedChangeListener(new AlarmStateListener(presenter));
        chooseAlarmBtn.setOnClickListener(new ButtonClickListener(presenter, sharedPreferenceUtils));
        setPasswrodBtn.setOnClickListener(new ButtonClickListener(presenter, sharedPreferenceUtils));
    }

    @Override
    public void showPasswordChangeDialog(String prevPassword) {

        PasswordChangeDialogFragment fragment = new PasswordChangeDialogFragment(presenter);
        fragment.show(getSupportFragmentManager(),PasswordChangeDialogFragment.class.getSimpleName());
    }

    @Override
    public void showError(String title, String message) {

    }

    @Override
    public String getResourceString(int stringId) {
        return getString(stringId);
    }
}
