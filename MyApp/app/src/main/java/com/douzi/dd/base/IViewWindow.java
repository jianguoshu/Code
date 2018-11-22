package com.douzi.dd.base;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by douzi on 2017/8/25.
 */

public interface IViewWindow<P extends ViewGroup.MarginLayoutParams, V extends ViewGroup> {
    void display(@NonNull View contentView, P params);

    boolean isDisplaying();

    boolean isALive();

    void hide();

    boolean isImmersiveMode();

    void setImmersiveMode(boolean isImmersiveMode);

    V getDecor();

    ViewGroup getContentView();

    void setOnWindowDismissListener(OnWindowDismissListener listener);

    void setTouchable(boolean touchable);

    boolean isTouchable();

    void setFocusable(boolean focusable);

    boolean isFocusable();

    void setTouchModal(boolean touchModal);

    boolean isTouchModal();

    void setOutsideTouchable(boolean touchable);

    boolean isOutsideTouchable();

    void setWindowLayoutParams(WindowManager.LayoutParams windowLayoutParams);

    @NonNull
    WindowManager.LayoutParams getWindowLayoutParams();

    interface OnWindowDismissListener{
        void onWindowDismiss();
    }
}
