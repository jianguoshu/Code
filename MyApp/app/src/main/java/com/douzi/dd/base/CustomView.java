package com.douzi.dd.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.douzi.dd.BaseActivity;

public abstract class CustomView<C extends IViewCallBack> implements IView<C> {
    protected @NonNull
    IViewWindow<ViewWindow.WindowDecorView.LayoutParams, ViewWindow.WindowDecorView> mWindow;
    protected @NonNull
    BaseActivity mContext;
    protected @Nullable
    C mViewCallBack;
    private boolean autoCancel;

    public CustomView(@NonNull BaseActivity activity) {
        this(activity, true);
    }

    public CustomView(@NonNull BaseActivity activity, boolean autoCancel) {
        this.mContext = activity;
        this.autoCancel = autoCancel;
        this.mWindow = initWindow();
    }

    @Override
    public void display(@Nullable final C callBack) {
        if (mContext.isFinishOrDestroy()) {
            return;
        }

        hide();
        mViewCallBack = callBack;

        ViewGroup parent = mWindow.getDecor();
        mWindow.display(getContentView(mContext, parent), null);
    }

    @Override
    public void displayConfirmView() {

    }

    @Override
    public abstract void displayLoadingView();

    @Override
    public abstract void displaySucView();

    @Override
    public abstract void displayFailView();

    @Override
    public void hide() {
        mWindow.hide();
    }

    @Override
    public boolean isDisplaying() {
        return mWindow.isDisplaying();
    }

    @Override
    public boolean isAlive() {
        return mWindow.isALive();
    }

    @Override
    public void setAutoCancel(boolean autoCancel) {
        this.autoCancel = autoCancel;
    }

    @Override
    public boolean isAutoCancel() {
        return autoCancel;
    }

    protected @NonNull
    IViewWindow<FrameLayout.LayoutParams, ViewWindow.WindowDecorView> initWindow() {
        final IViewWindow<FrameLayout.LayoutParams, ViewWindow.WindowDecorView> window = new ViewWindow(mContext) {
            @Override
            public boolean onBackKeyClicked() {
                if (mViewCallBack != null) {
                    mViewCallBack.onCancelBtnClicked();
                }
                if (isAutoCancel()) {
                    return super.onBackKeyClicked();
                } else {
                    return true;
                }
            }

            @Override
            public boolean onTouchOutside() {
                if (mViewCallBack != null) {
                    mViewCallBack.onCancelBtnClicked();
                }
                if (isAutoCancel()) {
                    return super.onTouchOutside();
                } else {
                    return true;
                }
            }
        };
        window.setImmersiveMode(true);
        return window;
    }

    protected abstract @NonNull
    View getContentView(final Context context, ViewGroup parent);
}
