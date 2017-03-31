package com.rcmapps.safetycharger.presenters;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.interfaces.MainView;
import com.rcmapps.safetycharger.models.PasswordChanger;

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

    public void confirmNewPassword(PasswordChanger passwordChanger) {
        PasswordChanger.Response response = passwordChanger.isValid();

        if(!response.isValid){
            view.showError("",response.message);
            return;
        }

        view.saveNewPassword(passwordChanger.getNewPassword());
        view.closePasswordChangeDialog();
        view.showConfirmation(view.getResourceString(R.string.password_change_successful));
    }

    public void cancelPasswordChange() {
        view.closePasswordChangeDialog();
    }

    public MainView getMainView(){
        return view;
    }
}
