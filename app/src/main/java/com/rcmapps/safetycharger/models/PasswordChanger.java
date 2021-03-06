package com.rcmapps.safetycharger.models;


import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.interfaces.BaseView;

public class PasswordChanger {

    private String newPassword;
    private String confirmPassword;
    private BaseView baseView;

    public PasswordChanger(BaseView baseView, String newPassword, String confirmPassword) {
        this.baseView = baseView;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public Response isValid() {
        if (newPassword == null || confirmPassword == null || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            return new Response(false, baseView.getResourceString(R.string.newpassword_notnull));
        }

        if (!newPassword.equals(confirmPassword)) {
            return new Response(false, baseView.getResourceString(R.string.password_not_match));
        }

        return new Response(true, "");

    }

    public class Response {
        public boolean isValid;
        public String message;

        public Response(boolean isValid, String message) {
            this.isValid = isValid;
            this.message = message;
        }
    }
}
