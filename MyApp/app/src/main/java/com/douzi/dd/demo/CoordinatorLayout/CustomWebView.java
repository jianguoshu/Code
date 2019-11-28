package com.douzi.dd.demo.CoordinatorLayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

public class CustomWebView extends WebView {
    public CustomWebView(Context context) {
        super(context);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        Log.i("test", "overScrollBy(" + deltaX + ", " + deltaY + ", " + scrollX + ", " + scrollY + ", " + scrollRangeX + ", " + scrollRangeY + ", " + maxOverScrollX + ", " + maxOverScrollY + ", " + isTouchEvent + ")");
        boolean b = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
        Log.i("test", "overScrollBy-----end : " + b);
        return b;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        Log.i("test", "onOverScrolled(" + scrollX + ", " + scrollY + ", " + clampedX + ", " + clampedY + ")");
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.i("test", "onScrollChanged(" + l + ", " + t + ", " + oldl + ", " + oldt + ")");
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void flingScroll(int vx, int vy) {
        Log.i("test", "flingScroll(" + vx + ", " + vy + ")");
        super.flingScroll(vx, vy);
    }
}
