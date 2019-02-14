package com.douzi.dd;

import android.app.Application;

import com.douzi.dd.utils.DeviceUtil;

public class MyApplication extends Application {
    private static MyApplication instance;
    private boolean isAppForeground = false;

    public MyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DeviceUtil.init(this);
    }

    public void setAppForeground(boolean isAppForeground) {
        this.isAppForeground = isAppForeground;
    }

    public boolean isAppForeground() {
        return isAppForeground;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
