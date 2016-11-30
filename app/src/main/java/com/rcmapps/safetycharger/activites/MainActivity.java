package com.rcmapps.safetycharger.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.interfaces.MainView;
import com.rcmapps.safetycharger.listeners.AlarmStateListener;
import com.rcmapps.safetycharger.listeners.ButtonClickListener;
import com.rcmapps.safetycharger.presenters.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

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
        chooseAlarmBtn.setOnClickListener(new ButtonClickListener(presenter));
        setPasswrodBtn.setOnClickListener(new ButtonClickListener(presenter));
    }

}
