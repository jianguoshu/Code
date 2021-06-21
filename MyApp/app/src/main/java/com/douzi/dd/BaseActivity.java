package com.douzi.dd;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.douzi.dd.base.AppFBGroundObserveHelper;
import com.douzi.dd.base.AppFBGroundObserver;
import com.douzi.dd.utils.Logger;

public class BaseActivity extends FragmentActivity implements AppFBGroundObserver {

    private boolean mIsDestroyed;
    protected static final AppFBGroundObserveHelper lifecycleObserveHelper = new AppFBGroundObserveHelper();
    private AppFBGroundObserver appFBGroundObserver = new AppFBGroundObserver() {
        @Override
        public void onAppForeground() {
            Logger.i("Lifecycle-observer", BaseActivity.this + "onAppForeground");
        }

        @Override
        public void onAppBackground() {
            Logger.i("Lifecycle-observer", BaseActivity.this + "onAppBackground");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleObserveHelper.observe(this);
        lifecycleObserveHelper.getAppFBGroundObservable().registerObserver(appFBGroundObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsDestroyed = true;
        lifecycleObserveHelper.getAppFBGroundObservable().unregisterObserver(appFBGroundObserver);
    }

    public boolean isFinishOrDestroy() {
        return isFinishing() || isDestroyed2();
    }

    public boolean isDestroyed2() {
        return mIsDestroyed;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (onBackKeyDown()) {
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    protected boolean onBackKeyDown() {
        return false;
    }

    @Override
    public void onAppForeground() {
        Logger.i("Lifecycle", this + "onAppForeground");
    }

    @Override
    public void onAppBackground() {
        Logger.i("Lifecycle", this + "onAppBackground");
    }
}

