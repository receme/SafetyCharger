package com.rcmapps.safetycharger.presenters;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.interfaces.MainView;

public class MainPresenter {

    private MainView view;
    private String prevPassword;

    public MainPresenter(MainView _view){
        this.view = _view;
    }

    public void init() {
        view.defineClickListener();
    }

    public void setPassword(String prevPassword) throws IllegalArgumentException {

        if(prevPassword == null){
            throw new IllegalArgumentException("previous password cannot be null");
        }

        view.showPasswordChangeDialog(prevPassword);
    }

    public String getPreviousPassword() {

        if(prevPassword == null){
            return "";
        }

        return prevPassword;
    }

    public void confirmNewPassword(String newPassword, String confirmNewPassword) {


    }

    public void cancelPasswordChange() {


    }
}
