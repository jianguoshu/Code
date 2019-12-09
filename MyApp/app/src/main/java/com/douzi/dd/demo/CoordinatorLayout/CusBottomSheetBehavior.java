package com.douzi.dd.demo.CoordinatorLayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.util.AttributeSet;
import android.view.View;

public class CusBottomSheetBehavior<V extends View> extends BottomSheetBehavior<V> {

    private float curSlide = 0;

    public CusBottomSheetBehavior() {
        super();
    }

    public CusBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setBottomSheetCallback(final BottomSheetCallback callback) {
        super.setBottomSheetCallback(new BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (callback != null) {
                    callback.onStateChanged(view, i);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                curSlide = v;
                if (callback != null) {
                    callback.onSlide(view, v);
                }
            }
        });
    }

    public float getCurSlide() {
        return curSlide;
    }
}
