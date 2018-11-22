package com.douzi.dd.utils;

import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.FloatRange;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;

public class ViewUtils {
    protected static final String TAG = "ViewUtils";
    private static long lastClickTime;

    public static void bindClickState(final View bindableView, final View[] bindedViews) {
        bindableView.setOnTouchListener(new View.OnTouchListener() {

            private boolean isInside = false;

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isInside = true;
                        for (View view : bindedViews) {
                            if (view != null) {
                                view.setSelected(true);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (isInside && !isViewTouchedIn(bindableView, event, true)) {
                            for (View view : bindedViews) {
                                if (view != null) {
                                    view.setSelected(false);
                                }
                            }
                            isInside = false;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        for (View view : bindedViews) {
                            if (view != null) {
                                view.setSelected(false);
                            }
                        }
                        // 使得bindableView可以被点击
                        if (isInside) {
                            bindableView.setPressed(true);
                        }
                        isInside = false;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        for (View view : bindedViews) {
                            if (view != null) {
                                view.setSelected(false);
                            }
                        }
                        isInside = false;
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 只是判断触摸点是否在view范围内，不能确定event是否传递给了view
     *
     * @param view
     * @param event
     * @param defaultIn 当view获取宽、高异常时的返回值
     * @return 触摸点是否在指定View范围内
     */
    public static boolean isViewTouchedIn(View view, MotionEvent event, boolean defaultIn) {
        int width = view.getWidth();
        int height = view.getHeight();
        if (width <= 0 || height <= 0) {
            return defaultIn;
        }
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        int[] locationOnScreen = new int[2];
        view.getLocationOnScreen(locationOnScreen);
        return rawX > locationOnScreen[0] && rawX < locationOnScreen[0] + width
                && rawY > locationOnScreen[1] && rawY < locationOnScreen[1] + height;
    }

    /**
     * change the v's visibility
     *
     * @param v
     */
    public static void switchVisiblity(View v) {
        v.setVisibility(v.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    /**
     * after this , u can call {@link android.view.View#getMeasuredHeight()},
     * {@link android.view.View#getMeasuredWidth()()} to get the view's size
     *
     * @param v
     */
    public static void measureView(View v) {
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    }

    public static void removeParentIfExist(View view) {
        try {
            if (view.getParent() instanceof ViewGroup) {
                ViewGroup parent = (ViewGroup) view.getParent();
                if (parent != null) {
                    parent.removeView(view);
                }
            }
        } catch (Exception e) {

        }
    }

    public static void refuseClickAWhile(final View view) {
        view.setClickable(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                view.setClickable(true);
            }
        }, 300);
    }

    public static void scrollToBottom(final ScrollView scroll, Handler mHandler) {

        mHandler.post(new Runnable() {
            public void run() {
                if (scroll == null) {
                    return;
                }
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static void visibleOrGone(View view, boolean b) {
        if (view != null) {
            view.setVisibility(b ? View.VISIBLE : View.GONE);
        }
    }

    public static void visibleOrInVisible(View view, boolean b) {
        if (view != null) {
            view.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public static boolean isVisible(View view) {
        if (view != null) {
            return view.getVisibility() == View.VISIBLE;
        }

        return false;
    }


    public static void setProgressBarColor(ProgressBar progressBar, Integer color) {
        Drawable drawable;
        if (progressBar.isIndeterminate()) {
            drawable = progressBar.getIndeterminateDrawable();
        } else {
            drawable = progressBar.getProgressDrawable();
        }

        if (drawable == null) {
            return;
        }

        if (color != null) {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        } else {
            drawable.clearColorFilter();
        }
    }


    public static void setTouchAlpha(View view) {
        setTouchAlpha(view, DEF_ALPHA, view);
    }

    public static final float DEF_ALPHA = 0.7f;

    public static void setTouchAlpha(View touchView, @FloatRange(from = 0.0, to = 1.0) final float alpha,
                                     final View... alphaViews) {
        if (touchView == null) {
            return;
        }
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        for (View view : alphaViews) {
                            view.setAlpha(alpha);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        for (View view : alphaViews) {
                            view.setAlpha(1);
                        }
                        break;
                }
                //这里一定要return false，不然该方法会拦截事件，造成不能响应点击等操作
                return false;
            }
        });
    }

    /**
     * 扩大信息流关闭按钮点击区域
     */
    public static void expandCloseOption(View view){
        expandViewTouchDelegate(view, 10,10,80,50);
    }

    /**
     * 扩大View的触摸和点击响应范围,最大不超过其父View范围
     *
     * @param view
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    public static void expandViewTouchDelegate(final View view, final int top,
                                               final int bottom, final int left, final int right) {

        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.setEnabled(true);
                view.getHitRect(bounds);

                bounds.top -= top;
                bounds.bottom += bottom;
                bounds.left -= left;
                bounds.right += right;

                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }

}
