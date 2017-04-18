package com.rcmapps.safetycharger.presenters;

import com.rcmapps.safetycharger.interfaces.MainView;
import com.rcmapps.safetycharger.models.PasswordChanger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MainPresenterTest {

    private MainPresenter presenter;
    private MainView mockView;

    @Before
    public void init() {
        mockView = mock(MainView.class);
        presenter = new MainPresenter(mockView);
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void setPassword_withNullPrevPassword_willThrowIllegalArgumentException() {
//
//        try {
//            presenter.setPassword(null);
//        } catch (IllegalArgumentException e) {
//            Assert.assertEquals(e.getMessage(), "previous password cannot be null");
//            throw e;
//        }
//    }
//
//    @Test
//    public void setPassword_withValidPrevPassword_willShowAlertDialogWithPrevPasswordInField() {
//        presenter.setPassword(anyString());
//        verify(mockView, times(1)).showPasswordChangeDialog(anyString());
//    }
//
//    @Test
//    public void confirmPassword_whenNewPasswordValidationFailed_willShowError() {
//        when(mockView.getResourceString(anyInt())).thenReturn("");
//        PasswordChanger changer = new PasswordChanger(mockView, null, "");
//        presenter.confirmNewPassword(changer);
//        verify(mockView, times(1)).showError(anyString(), anyString());
//    }
//
//    @Test
//    public void confirmPassword_whenNewPasswordValidationSucceed_passwordChanged() {
//        when(mockView.getResourceString(anyInt())).thenReturn("");
//        String newPassword = "11111111";
//        String confirmPassword = newPassword;
//        PasswordChanger changer = new PasswordChanger(mockView, newPassword, confirmPassword);
//        presenter.confirmNewPassword(changer);
//        verify(mockView,times(1)).saveNewPassword(newPassword);
//    }
//
//    @Test
//    public void confirmPassword_whenNewPasswordValidationSucceed_passwordChangeDialogWillBeClosed() {
//        when(mockView.getResourceString(anyInt())).thenReturn("");
//        String newPassword = "11111111";
//        String confirmPassword = newPassword;
//        PasswordChanger changer = new PasswordChanger(mockView, newPassword, confirmPassword);
//        presenter.confirmNewPassword(changer);
//        verify(mockView,times(1)).closePasswordChangeDialog();
//        verify(mockView,times(1)).showToast(anyString());
//    }
//
//    @Test
//    public void cancelPasswordChange_willClosePasswordChangeDialog() {
//
//        presenter.cancelPasswordChange();
//        verify(mockView,times(1)).closePasswordChangeDialog();
//    }

    @Test
    public void onCheckedChanged_withisCheckedFalse_willStopSafetyAlarm(){
        presenter.onCheckedChanged(false);
        verify(mockView,times(1)).stopSafetyAlarm();
    }

    @Test
    public void onCheckedChanged_withisCheckedTrueButPasswordNotSet_willShowErrorMessageAndUncheckCheckbox(){
        when(mockView.isPasswordSet()).thenReturn(false);
        when(mockView.getResourceString(anyInt())).thenReturn("");
        presenter.onCheckedChanged(true);
        verify(mockView,times(0)).setSafetyAlarm();
        verify(mockView,times(1)).showToast(anyString());
        verify(mockView,times(1)).uncheckAlarmSwitch();
    }

    @Test
    public void onCheckedChanged_withisCheckedTrueAndPasswordSet_willSetSafetyAlarm(){
        when(mockView.isPasswordSet()).thenReturn(true);
        presenter.onCheckedChanged(true);
        verify(mockView,times(1)).setSafetyAlarm();
        verify(mockView,times(0)).showToast(anyString());
        verify(mockView,times(0)).uncheckAlarmSwitch();
    }

    @Test
    public void checkIfAlarmStarted_withAlarmOn_willShowEnterPasswordDialog(){
        presenter.checkIfAlarmStarted(true);
        verify(mockView,only()).showEnterPasswordDialog();
        verify(mockView,never()).closeEnterPasswordDialog();
    }

    @Test
    public void checkIfAlarmStarted_withAlarmOff_willCloseEnterPasswordDialog(){
        presenter.checkIfAlarmStarted(false);
        verify(mockView,only()).closeEnterPasswordDialog();
        verify(mockView,never()).showEnterPasswordDialog();
    }

    @Test
    public void checkCableStatus_withCableConnected_willShowEnterPasswordDialog(){
        presenter.checkCableStatus(true);
        verify(mockView,only()).closeEnterPasswordDialog();
        verify(mockView,never()).showEnterPasswordDialog();
    }

    @Test
    public void checkCableStatus_withCableDisconnected_willCloseEnterPasswordDialog(){
        presenter.checkCableStatus(false);
        verify(mockView,only()).showEnterPasswordDialog();
        verify(mockView,never()).closeEnterPasswordDialog();
    }

}
