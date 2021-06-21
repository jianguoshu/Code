package com.douzi.dd.base;

import android.database.Observable;

public class AppFBGroundObservable extends Observable<AppFBGroundObserver> {
    public void notifyBackground() {
        synchronized (mObservers) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onAppBackground();
            }
        }
    }

    public void notifyForeground() {
        synchronized (mObservers) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onAppForeground();
            }
        }
    }

    @Override
    public void registerObserver(AppFBGroundObserver observer) {
        synchronized (mObservers) {
            if (mObservers.contains(observer)) {
                return;
            }
            mObservers.add(observer);
        }
    }

    @Override
    public void unregisterObserver(AppFBGroundObserver observer) {
        synchronized (mObservers) {
            int index = mObservers.indexOf(observer);
            if (index == -1) {
                return;
            }
            mObservers.remove(index);
        }
    }
}