package com.himanshu.customergludemolibrary;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Interface.DataListner;
import com.customerglu.sdk.Modal.RegisterModal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;
@RunWith(JUnit4.class)
public class CustomerGluAndroidTest {

    @Test
    public void is_registration_successful()
    {
        //  ApiClients.getClient()
      //  CustomerGlu.getInstance().callApi();

//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//
//        Map<String,Object> registerData = new HashMap<>();
//        registerData.put("userId","testuser-1");
//
//        CustomerGlu.getInstance().registerDevice(appContext, registerData, false, new DataListner() {
//            @Override
//            public void onSuccess(RegisterModal registerModal) {
//                assertEquals("testuser-12",registerModal.getData().getUser().getUserId());
//            }
//
//            @Override
//            public void onFail(String message) {
//
//            }
//        });
    }
}
