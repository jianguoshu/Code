package com.douzi.dd.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.douzi.dd.BaseActivity;

/**
 * Created by douzi on 2017/8/25.
 */

public class ViewWindow implements IViewWindow<ViewWindow.WindowDecorView.LayoutParams, ViewWindow.WindowDecorView> {

    public static final int SIZE_UNDEFINED = -3;

    private boolean isDisplaying;
    private WindowDecorView decorView;
    private WindowManager mWindowManager;
    protected BaseActivity mContext;
    protected View mContentView;
    private OnWindowDismissListener mWindowDismissListener;
    private boolean isImmersiveMode = false; //沉浸式设置，这里默认是非沉浸式，即状态栏黑框，导航栏黑框

    public ViewWindow(@NonNull BaseActivity mContext) {
        this.mContext = mContext;
    }

    @Override
    public void display(@NonNull View contentView, ViewWindow.WindowDecorView.LayoutParams params) {
        reset();

        if (contentView instanceof WindowDecorView) {
            decorView = (WindowDecorView) contentView;
            mContentView = ((WindowDecorView) contentView).getChildAt(0);
        } else {
            mContentView = contentView;
            getDecor().addView(contentView, params);
        }

        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams decorParams = getWindowLayoutParams();

        updateWindowLayoutParams(decorParams);

        mWindowManager.addView(decorView, decorParams);

        isDisplaying = true;
    }

    private void updateWindowLayoutParams(@NonNull WindowManager.LayoutParams windowParams) {
        if (mContentView != null) {
            ViewGroup.LayoutParams layoutParams = mContentView.getLayoutParams();
            if (layoutParams != null) {
                if (windowParams.width == SIZE_UNDEFINED && layoutParams.width == FrameLayout.LayoutParams.MATCH_PARENT) {
                    windowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                }
                if (windowParams.height == SIZE_UNDEFINED && layoutParams.height == FrameLayout.LayoutParams.MATCH_PARENT) {
                    windowParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                }
            }
        }
        if (windowParams.width == SIZE_UNDEFINED) {
            windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        if (windowParams.height == SIZE_UNDEFINED) {
            windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }

        if (isImmersiveMode) {
            windowParams.type = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN;
        }

        if (!mTouchable) {
            windowParams.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        }

        if (!mFocusable) {
            windowParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        }

        if (mNotTouchModal) {
            windowParams.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        }

        if (mOutsideTouchable) {
            windowParams.flags |= WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        }
    }

    @Override
    public boolean isDisplaying() {
        return isALive() && isDisplaying;
    }

    @Override
    public boolean isALive() {
        return mContext != null && !mContext.isFinishOrDestroy();
    }

    @Override
    public void hide() {
        if (isDisplaying()) {
            reset();
            if (mWindowDismissListener != null) {
                mWindowDismissListener.onWindowDismiss();
            }
        }
    }

    @Override
    public boolean isImmersiveMode() {
        return isImmersiveMode;
    }

    @Override
    public void setImmersiveMode(boolean isImmersiveMode) {
        this.isImmersiveMode = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isImmersiveMode;
    }

    private void reset() {
        if (decorView != null && decorView.getParent() != null) {
            mWindowManager.removeView(decorView);
        }
        mWindowManager = null;
        decorView = null;
        mContentView = null;
        isDisplaying = false;
    }

    @Override
    public @NonNull
    WindowDecorView getDecor() {
        if (decorView == null) {
            decorView = new WindowDecorView(mContext);
        }
        return decorView;
    }

    @Override
    public @Nullable
    ViewGroup getContentView() {
        return mContentView instanceof ViewGroup ? (ViewGroup) mContentView : null;
    }

    @Override
    public void setOnWindowDismissListener(OnWindowDismissListener listener) {
        mWindowDismissListener = listener;
    }

    public boolean onBackKeyClicked() {
        hide();
        return true;
    }

    public boolean onTouchOutside() {
        hide();
        return true;
    }

    public final class WindowDecorView extends FrameLayout {

        public WindowDecorView(Context context) {
            super(context);
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if (getKeyDispatcherState() == null) {
                    return super.dispatchKeyEvent(event);
                }

                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                    final KeyEvent.DispatcherState state = getKeyDispatcherState();
                    if (state != null) {
                        state.startTracking(event, this);
                    }
                    return true;
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    final KeyEvent.DispatcherState state = getKeyDispatcherState();
                    if (state != null && state.isTracking(event) && !event.isCanceled()) {
                        return onBackKeyClicked();
                    }
                }
                return super.dispatchKeyEvent(event);
            } else {
                return super.dispatchKeyEvent(event);
            }
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (mTouchInterceptor != null && mTouchInterceptor.onTouch(this, ev)) {
                return true;
            }
            return super.dispatchTouchEvent(ev);
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            final int x = (int) event.getX();
            final int y = (int) event.getY();

            if ((event.getAction() == MotionEvent.ACTION_DOWN)
                    && ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight()))) {
                return onTouchOutside();
            } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                return onTouchOutside();
            } else {
                return super.onTouchEvent(event);
            }
        }
    }

    private View.OnTouchListener mTouchInterceptor;

    public void setTouchInterceptor(View.OnTouchListener listener) {
        mTouchInterceptor = listener;
    }

    private boolean mTouchable = true;

    @Override
    public void setTouchable(boolean touchable) {
        mTouchable = touchable;
    }

    @Override
    public boolean isTouchable() {
        return mTouchable;
    }

    private boolean mFocusable = true;

    @Override
    public void setFocusable(boolean focusable) {
        mFocusable = focusable;
    }

    @Override
    public boolean isFocusable() {
        return mFocusable;
    }

    private boolean mNotTouchModal;

    @Override
    public void setTouchModal(boolean touchModal) {
        mNotTouchModal = !touchModal;
    }

    @Override
    public boolean isTouchModal() {
        return !mNotTouchModal;
    }

    private boolean mOutsideTouchable;

    @Override
    public void setOutsideTouchable(boolean touchable) {
        mOutsideTouchable = touchable;
    }

    @Override
    public boolean isOutsideTouchable() {
        return mOutsideTouchable;
    }

    private WindowManager.LayoutParams mWindowLayoutParams;

    @Override
    public void setWindowLayoutParams(WindowManager.LayoutParams windowLayoutParams) {
        this.mWindowLayoutParams = windowLayoutParams;
    }

    @Override
    public @NonNull
    WindowManager.LayoutParams getWindowLayoutParams() {
        if (mWindowLayoutParams == null) {
            mWindowLayoutParams = new WindowManager.LayoutParams();
            mWindowLayoutParams.height = SIZE_UNDEFINED;
            mWindowLayoutParams.width = SIZE_UNDEFINED;
            mWindowLayoutParams.gravity = Gravity.CENTER;
            mWindowLayoutParams.format = PixelFormat.TRANSLUCENT; // 显示效果
        }
        return mWindowLayoutParams;
    }
}
