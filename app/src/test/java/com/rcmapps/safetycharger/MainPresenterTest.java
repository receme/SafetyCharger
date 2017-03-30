package com.rcmapps.safetycharger;

import com.rcmapps.safetycharger.interfaces.MainView;
import com.rcmapps.safetycharger.presenters.MainPresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by receme on 3/31/17.
 */

public class MainPresenterTest {

    private MainPresenter presenter;
    private MainView mockView;
    @Before
    public void init(){
        mockView = mock(MainView.class);
        presenter = new MainPresenter(mockView);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setPassword_withNullPrevPassword_willThrowIllegalArgumentException(){

        try {
            presenter.setPassword(null);
        }catch (IllegalArgumentException e){
            Assert.assertEquals(e.getMessage(),"previous password cannot be null");
            throw e;
        }
    }

    @Test
    public void setPassword_withValidPrevPassword_willShowAlertDialogWithPrevPasswordInField(){
        presenter.setPassword(anyString());
        verify(mockView,times(1)).showPasswordChangeDialog(anyString());
    }

}
