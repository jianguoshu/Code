package com.douzi.dd.demo.CoordinatorLayout;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

public class AlphaBehavior2 extends CoordinatorLayout.Behavior<View> {

    public AlphaBehavior2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int i = parent.getHeight() - ((AppBarLayout) dependency).getTotalScrollRange();
        child.setAlpha(1 - 1.0f * (Math.abs(dependency.getTop()) - i) / ((AppBarLayout) dependency).getTotalScrollRange());
        return true;
    }

}
