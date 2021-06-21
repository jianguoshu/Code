package com.douzi.dd;

import android.app.Application;

import com.douzi.dd.utils.DeviceUtil;

public class MyApplication extends Application {
    private static MyApplication instance;

    public MyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DeviceUtil.init(this);
    }



    public static MyApplication getInstance() {
        return instance;
    }
}
