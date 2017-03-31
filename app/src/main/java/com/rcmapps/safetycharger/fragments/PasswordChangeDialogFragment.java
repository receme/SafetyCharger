package com.rcmapps.safetycharger.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.models.PasswordChanger;
import com.rcmapps.safetycharger.presenters.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by receme on 3/31/17.
 */

public class PasswordChangeDialogFragment extends AppCompatDialogFragment implements View.OnClickListener{

    @BindView(R.id.confirmBtn)
    Button confirmBtn;
    @BindView(R.id.cancelBtn)
    Button cancelBtn;
    @BindView(R.id.prevPassword)
    EditText prevPasswordEdtxt;
    @BindView(R.id.newPasswordEdtxt)
    EditText newPasswordEdtxt;
    @BindView(R.id.confirmPasswordEdtxt)
    EditText confirmPasswordEdtxt;

    private MainPresenter presenter;
    private String newPassword ="";
    private String confirmNewPassword ="";

    public PasswordChangeDialogFragment(){
    }

    public PasswordChangeDialogFragment(MainPresenter presenter) {
        this.presenter = presenter;
    }

    public PasswordChangeDialogFragment(MainPresenter presenter, @NonNull String newPassword, @NonNull String confirmNewPassword) {
        this.presenter = presenter;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_password_change,container,false);
        ButterKnife.bind(this,view);

        init();
        return view;
    }

    private void init() {

        prevPasswordEdtxt.setText(presenter.getPreviousPassword());
        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        newPasswordEdtxt.setText(newPassword);
        confirmPasswordEdtxt.setText(confirmNewPassword);

        setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(confirmBtn)){
            presenter.confirmNewPassword(new PasswordChanger(newPasswordEdtxt.getText().toString(),confirmPasswordEdtxt.getText().toString()));
        }
        else if(view.equals(cancelBtn)){
            presenter.cancelPasswordChange();
        }
    }
}
