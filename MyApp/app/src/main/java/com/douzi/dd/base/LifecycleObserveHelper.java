package com.douzi.dd.base;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import java.util.HashMap;

public abstract class LifecycleObserveHelper<T extends LifecycleOwner> {
    private HashMap<T, LifecycleObserver> lifecycleObserverMap = new HashMap<>();

    public void observe(final T owner) {
        LifecycleObserver observer = lifecycleObserverMap.get(owner);
        if (observer == null) {
            observer = createLifecycleObserver(owner);
            lifecycleObserverMap.put(owner, observer);
        }
        owner.getLifecycle().addObserver(observer);
    }

    protected abstract @NonNull LifecycleObserver createLifecycleObserver(final T owner);

    public void unObserve(T owner) {
        LifecycleObserver observer = lifecycleObserverMap.get(owner);
        if (observer != null) {
            owner.getLifecycle().removeObserver(observer);
        }
        lifecycleObserverMap.remove(owner);
    }
}
