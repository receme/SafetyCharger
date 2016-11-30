package com.rcmapps.safetycharger.presenters;

import com.rcmapps.safetycharger.interfaces.MainView;

/**
 * Created by receme on 12/1/16.
 */
public class MainPresenter {

    MainView view;

    public MainPresenter(MainView _view){
        this.view = _view;
    }

    public void init() {
        view.defineClickListener();
    }

}
