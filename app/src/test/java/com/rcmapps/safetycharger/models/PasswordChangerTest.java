package com.rcmapps.safetycharger.models;


import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.interfaces.BaseView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PasswordChangerTest {

    private BaseView baseView;
    private PasswordChanger passwordChanger;

    @Before
    public void init() {
        baseView = mock(BaseView.class);
    }

    @Test
    public void isValid_withNullNewPassword_willReturnFalse() throws Exception {
        when(baseView.getResourceString(R.string.newpassword_notnull)).thenReturn("");
        passwordChanger = new PasswordChanger(baseView, null, "1213");
        PasswordChanger.Response response = passwordChanger.isValid();
        Assert.assertEquals(false, response.isValid);
    }

    @Test
    public void isValid_withNullConfirmPassword_willReturnFalse() throws Exception {
        when(baseView.getResourceString(R.string.newpassword_notnull)).thenReturn("");
        passwordChanger = new PasswordChanger(baseView, "1223", null);
        PasswordChanger.Response response = passwordChanger.isValid();
        Assert.assertEquals(false, response.isValid);
    }

    @Test
    public void isValid_withEmptyNewPassword_willReturnFalse() throws Exception {
        when(baseView.getResourceString(R.string.newpassword_notnull)).thenReturn("");
        passwordChanger = new PasswordChanger(baseView, "", "121212");
        PasswordChanger.Response response = passwordChanger.isValid();
        Assert.assertEquals(false, response.isValid);
    }

    @Test
    public void isValid_withEmptyConfirmPassword_willReturnFalse() throws Exception {
        when(baseView.getResourceString(R.string.newpassword_notnull)).thenReturn("");
        passwordChanger = new PasswordChanger(baseView, "12122", "");
        PasswordChanger.Response response = passwordChanger.isValid();
        Assert.assertEquals(false, response.isValid);
    }

    @Test
    public void isValid_withNewPasswordHavingCharLessThanEight_willReturnFalse() throws Exception {
        when(baseView.getResourceString(R.string.newpassword_notnull)).thenReturn("");
        passwordChanger = new PasswordChanger(baseView, "12122", "12122");
        PasswordChanger.Response response = passwordChanger.isValid();
        Assert.assertEquals(false, response.isValid);
    }

    @Test
    public void isValid_withNonMatchingNewAndConfirmPassword_willReturnFalse() throws Exception {
        when(baseView.getResourceString(R.string.newpassword_notnull)).thenReturn("");
        passwordChanger = new PasswordChanger(baseView, "11111111", "11111112");
        PasswordChanger.Response response = passwordChanger.isValid();
        Assert.assertEquals(false, response.isValid);
    }

    @Test
    public void isValid_withValidNewAndConfirmPassword_willReturnTrue() throws Exception {
        when(baseView.getResourceString(R.string.newpassword_notnull)).thenReturn("");
        passwordChanger = new PasswordChanger(baseView, "11111111", "11111111");
        PasswordChanger.Response response = passwordChanger.isValid();
        Assert.assertEquals(true, response.isValid);
    }
}
