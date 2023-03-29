package com.customerglu.customerglulibrarytest;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

public class GetApplicationContext extends Application
{
    public static Application getApplication() {
        return ApplicationProvider.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
