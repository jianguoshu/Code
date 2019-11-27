package com.douzi.dd.demo.CoordinatorLayout;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.logging.Logger;

public class BottomAlphaBehavior2 extends CoordinatorLayout.Behavior<View> {

    public BottomAlphaBehavior2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setTranslationY(parent.getHeight() - child.getHeight());
        int i = parent.getHeight() - ((AppBarLayout) dependency).getTotalScrollRange();
        child.setAlpha(1.0f * (Math.abs(dependency.getTop()) - i) / ((AppBarLayout) dependency).getTotalScrollRange());
        return true;
    }
}
