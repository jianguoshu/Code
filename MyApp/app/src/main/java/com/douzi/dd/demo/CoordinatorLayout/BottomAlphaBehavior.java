package com.douzi.dd.demo.CoordinatorLayout;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

public class BottomAlphaBehavior extends CoordinatorLayout.Behavior<View> {

    public BottomAlphaBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setTranslationY(parent.getHeight() - child.getHeight());
        child.setAlpha(1.0f * Math.abs(dependency.getTop()) / 500);
        return true;
    }

}
