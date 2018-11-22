package com.douzi.dd.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.douzi.dd.BaseActivity;

public abstract class LoadingView extends CustomView<ILoadingViewCallBack> {

    public LoadingView(@NonNull BaseActivity activity) {
        this(activity, true);
    }

    public LoadingView(@NonNull BaseActivity activity, boolean autoCancel) {
        super(activity, autoCancel);
    }

    @Override
    public void display(@Nullable ILoadingViewCallBack callBack) {
        super.display(callBack);
        if (mViewCallBack != null) {
            mViewCallBack.onViewDisplay();
        }
    }

    @Override
    public abstract void displayLoadingView();

    @Override
    public abstract void displaySucView();

    @Override
    public abstract void displayFailView();

    @Override
    protected @NonNull
    IViewWindow<ViewWindow.WindowDecorView.LayoutParams, ViewWindow.WindowDecorView> initWindow() {
        IViewWindow<ViewWindow.WindowDecorView.LayoutParams, ViewWindow.WindowDecorView> window = super.initWindow();
        window.setOnWindowDismissListener(new IViewWindow.OnWindowDismissListener() {
            @Override
            public void onWindowDismiss() {
                if (mViewCallBack != null) {
                    mViewCallBack.onViewDismiss();
                }
            }
        });
        window.setTouchable(false);
        window.setFocusable(false);
        return window;
    }

    @Override
    protected abstract @NonNull
    View getContentView(Context context, ViewGroup parent);
}
