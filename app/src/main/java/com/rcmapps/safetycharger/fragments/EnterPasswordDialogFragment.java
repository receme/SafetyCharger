package com.rcmapps.safetycharger.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.presenters.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnterPasswordDialogFragment extends AppCompatDialogFragment {

    @BindView(R.id.confirmBtn)
    Button confirmBtn;
    @BindView(R.id.cancelBtn)
    Button cancelBtn;
    @BindView(R.id.passwordEdtxt)
    EditText passwordEdtxt;

    private MainPresenter presenter;

    public EnterPasswordDialogFragment() {
    }

    public void  setPresenter (MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enterpassword,container,false);

        ButterKnife.bind(this,view);
        setCancelable(false);
        defineClickListener();

        return view;
    }

    private void defineClickListener() {

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.validatePassword(passwordEdtxt.getText().toString());
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                presenter.closeApp();
            }
        });
    }

}
