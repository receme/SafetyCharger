package com.rcmapps.safetycharger.models;


import android.util.Pair;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.interfaces.BaseView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PasswordChangerTest {

    private BaseView baseView;
    private PasswordChanger passwordChanger;

    @Before
    public void init(){
        baseView = mock(BaseView.class);
    }

    @Test
    public void isValid_withNullNewPassword_willReturnFalse() throws Exception{
        when(baseView.getResourceString(R.string.newpassword_notnull)).thenReturn("");
        passwordChanger = new PasswordChanger(baseView,null,"");
        PasswordChanger.Response response = passwordChanger.isValid();
        Assert.assertEquals(false,response.isValid);
    }
}
