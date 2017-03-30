package com.rcmapps.safetycharger.presenters;

import com.rcmapps.safetycharger.interfaces.MainView;

public class MainPresenter {

    MainView view;

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
}
