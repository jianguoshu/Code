package com.douzi.dd.base;

import android.support.annotation.NonNull;

import com.douzi.dd.BaseActivity;

public abstract class ConfirmView extends CustomView<IConfirmViewCallBack> {

    public ConfirmView(@NonNull BaseActivity activity) {
        super(activity, true);
    }

    @NonNull
    @Override
    protected IViewWindow<ViewWindow.WindowDecorView.LayoutParams, ViewWindow.WindowDecorView> initWindow() {
        IViewWindow<ViewWindow.WindowDecorView.LayoutParams, ViewWindow.WindowDecorView> window = super.initWindow();
        window.setFocusable(true);
        window.setTouchable(true);
        return window;
    }

    @Override
    public void displayLoadingView() {

    }

    @Override
    public void displaySucView() {

    }

    @Override
    public void displayFailView() {

    }
}
