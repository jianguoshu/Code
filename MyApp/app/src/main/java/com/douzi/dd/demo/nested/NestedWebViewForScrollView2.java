package com.douzi.dd.demo.nested;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.webkit.WebView;

public class NestedWebViewForScrollView2 extends WebView implements NestedScrollingChild {
    private int mLastY;
    private int mDownY;
    private boolean canNested;
    private final int[] mScrollOffset = new int[2];
    private NestedScrollingChildHelper mChildHelper;
    private int[] mScrollConsumed;
    private boolean priorityForUp = false;
    private boolean priorityForDown = false;

    public NestedWebViewForScrollView2(Context context) {
        this(context, null);
    }

    public NestedWebViewForScrollView2(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.webViewStyle);
    }

    public NestedWebViewForScrollView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    private int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop() + 1;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean result = false;

        MotionEvent event = MotionEvent.obtain(ev);
        int y = (int) event.getY();
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                canNested = false;
                mLastY = y;
                mDownY = y;
                result = super.onTouchEvent(event);
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = mLastY - y;
                if (canNested) {
                    if (deltaY > 0 && priorityForUp) {
                        float range = getContentHeight() * getScale() - getScrollY() - getHeight();
                        deltaY = (int) Math.max(0, deltaY - range);
                    }
                    if (deltaY < 0 && priorityForDown) {
                        deltaY = Math.min(0, deltaY + getScrollY());
                    }
                    if (deltaY != 0) {
                        if (dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset)) {
                            if (dispatchNestedScroll(0, 0, 0, deltaY, mScrollOffset)) {
                                mLastY = y - mScrollOffset[1];
                                event.offsetLocation(0, -mScrollOffset[1]);
                            }
                        }
                    }
                } else {
                    mLastY = y;
                    deltaY = mDownY - y;
                    if (Math.abs(deltaY) >= touchSlop) {
                        canNested = true;
                        int dyExtra = 0;
                        if (deltaY > 0) {
                            dyExtra = deltaY - touchSlop;
                        } else if (deltaY < 0) {
                            dyExtra = deltaY + touchSlop;
                        }
                        if (dyExtra > 0 && priorityForUp) {
                            float range = getContentHeight() * getScale() - getScrollY() - getHeight();
                            dyExtra = (int) Math.max(0, dyExtra - range);
                        }
                        if (deltaY < 0 && priorityForDown) {
                            dyExtra = Math.min(0, dyExtra + getScrollY());
                        }
                        if (dyExtra != 0) {
                            if (dispatchNestedPreScroll(0, dyExtra, mScrollConsumed, mScrollOffset)) {
                                if (dispatchNestedScroll(0, 0, 0, dyExtra, mScrollOffset)) {
                                    mLastY = y - mScrollOffset[1];
                                    event.offsetLocation(0, -mScrollOffset[1]);
                                }
                            }
                        }
                    }
                }
                result = super.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                result = super.onTouchEvent(event);
                // end NestedScroll
                stopNestedScroll();
                break;
        }
        return result;
    }

    // Nested Scroll implements
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed,
                                        int[] offsetInWindow) {

        boolean b = mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
        Log.i("test", "dispatchNestedScroll(" + dxConsumed + "," + dyConsumed + "," + dxUnconsumed + "," + dyUnconsumed + ",offsetInWindow[" + offsetInWindow[0] + "," + offsetInWindow[1] + "]" + ")");
        return b;
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

}
