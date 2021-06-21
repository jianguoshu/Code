package com.douzi.dd.base;

import android.app.ActivityManager;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.NonNull;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.MyApplication;
import com.douzi.dd.utils.Logger;

import java.util.List;

public class AppFBGroundObserveHelper extends LifecycleObserveHelper<BaseActivity> {
    private ActivityManager mActivityManager;
    private boolean isFirstStartOrRestart = true;
    private boolean isExitApp = false;
    private BaseActivity activeActivity;
    private final AppFBGroundObservable appFBGroundObservable = new AppFBGroundObservable();

    public AppFBGroundObservable getAppFBGroundObservable() {
        return appFBGroundObservable;
    }

    @NonNull
    protected LifecycleObserver createLifecycleObserver(final BaseActivity activity) {
        LifecycleObserver lifecycleObserver;
        lifecycleObserver = new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            public void onStart() {
                if (!isAppForeground()) {
                    isExitApp = false;
                    setAppForeground(true);
                    if (!isFirstStartOrRestart) {
                        if (activity instanceof AppFBGroundObserver) {
                            ((AppFBGroundObserver) activity).onAppForeground();
                        }
                        appFBGroundObservable.notifyForeground();
                    } else {
                        isFirstStartOrRestart = false;
                    }
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            public void onResume() {
                activeActivity = activity;
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            public void onPause() {
                activeActivity = null;
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            public void onStop() {
                if (isAppForeground()) {
                    try {
                        if (activeActivity != null) {
                            return;
                        }
                        if (isExitApp) {
                            setAppForeground(false);
                            return;
                        }
                        if (mActivityManager == null) {
                            mActivityManager = (ActivityManager) MyApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
                        }

                        List<ActivityManager.RunningTaskInfo> tasks = mActivityManager.getRunningTasks(1);
                        if (!tasks.isEmpty()) {
                            ComponentName baseActivity = tasks.get(0).baseActivity;
                            if (!baseActivity.getPackageName().equals(activity.getPackageName())) {
                                setAppForeground(false);
                                if (activity instanceof AppFBGroundObserver) {
                                    ((AppFBGroundObserver) activity).onAppBackground();
                                }
                                appFBGroundObservable.notifyBackground();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            public void onDestroy() {
                AppFBGroundObserveHelper.this.unObserve(activity);
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
            public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
                if (Logger.SHOW_LOG) {
                    Logger.i("onAny", owner.toString() + "--" + event.toString());
                }
            }
        };
        return lifecycleObserver;
    }

    private boolean isAppForeground = false;

    private void setAppForeground(boolean isAppForeground) {
        this.isAppForeground = isAppForeground;
    }

    public boolean isAppForeground() {
        return isAppForeground;
    }

    public void exitApp() {
        isExitApp = true;
        isFirstStartOrRestart = true;
    }
}
