package com.douzi.dd.base;

import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewOutlineProvider;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class RoundCornerViewOutlineProvider extends ViewOutlineProvider {

    private float mRadius;

    public RoundCornerViewOutlineProvider(float radius) {
        this.mRadius = radius;
    }

    int width = 400;
    int height = 600;

//    @Override
//    public void getOutline(View view, Outline outline) {
//        Rect rect = new Rect();
//        view.getGlobalVisibleRect(rect);
//        int leftMargin = (int) (1.0f * (rect.right - rect.left - width) / 2);
//        int topMargin = (int) (1.0f * (rect.bottom - rect.top - height) / 2);
//        Rect selfRect = new Rect(leftMargin, topMargin,
//                rect.right - rect.left - leftMargin, rect.bottom - rect.top - topMargin);
//        outline.setRoundRect(selfRect, mRadius);
//    }

    @Override
    public void getOutline(View view, Outline outline) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        int leftMargin = 0;
        int topMargin = 0;
        Rect selfRect = new Rect(leftMargin, topMargin,
                rect.right - rect.left - leftMargin, rect.bottom - rect.top - topMargin);
        outline.setRoundRect(selfRect, mRadius);
    }
}
