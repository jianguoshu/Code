package com.douzi.dd;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.List;

public class BaseActivity extends FragmentActivity {

    private static ActivityManager mActivityManager;
    private boolean isAppBroughtToBackground = true;
    private boolean mIsDestroyed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isAppBroughtToBackground) {
            onAppForeground();
            isAppBroughtToBackground = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!isAppBroughtToBackground) {
            try {
                if (mActivityManager == null) {
                    mActivityManager = (ActivityManager) MyApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
                }

                List<ActivityManager.RunningTaskInfo> tasks = mActivityManager.getRunningTasks(1);
                if (!tasks.isEmpty()) {
                    ComponentName topActivity = tasks.get(0).topActivity;
                    if (!topActivity.getPackageName().equals(getPackageName())) {
                        isAppBroughtToBackground = true;
                        onAppBackground();
                        return;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsDestroyed = true;
    }

    public boolean isFinishOrDestroy() {
        return isFinishing() || isDestroyed2();
    }

    public boolean isDestroyed2() {
        return mIsDestroyed;
    }

    protected void onAppForeground() {
        MyApplication.getInstance().setAppForeground(true);
    }

    protected void onAppBackground() {
        MyApplication.getInstance().setAppForeground(false);
    }
}

