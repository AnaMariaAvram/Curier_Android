package com.courier.manager;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class CourierApp extends Application {

    public void onCreate() {
        super.onCreate();
    }
}
