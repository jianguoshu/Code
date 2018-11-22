package com.douzi.dd.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.douzi.dd.BaseActivity;

public abstract class ResultConfirmView extends CustomView<IResultConfirmViewCallBack> {

    public ResultConfirmView(@NonNull BaseActivity activity) {
        super(activity, true);
    }

    @Override
    public void display(IResultConfirmViewCallBack callBack) {
        super.display(callBack);
        if (mViewCallBack != null) {
            mViewCallBack.onViewDisplay();
        }
    }

    @Override
    public void displayLoadingView() {

    }

    @Override
    public abstract void displaySucView();

    @Override
    public abstract void displayFailView();

    @NonNull
    @Override
    protected IViewWindow<ViewWindow.WindowDecorView.LayoutParams, ViewWindow.WindowDecorView> initWindow() {
        IViewWindow<ViewWindow.WindowDecorView.LayoutParams, ViewWindow.WindowDecorView> window = super.initWindow();
        window.setTouchable(true);
        window.setFocusable(true);
        window.setOnWindowDismissListener(new IViewWindow.OnWindowDismissListener() {
            @Override
            public void onWindowDismiss() {
                if (mViewCallBack != null) {
                    mViewCallBack.onViewDismiss();
                }
            }
        });
        return window;
    }

    @NonNull
    @Override
    protected abstract View getContentView(Context context, ViewGroup parent);
}
