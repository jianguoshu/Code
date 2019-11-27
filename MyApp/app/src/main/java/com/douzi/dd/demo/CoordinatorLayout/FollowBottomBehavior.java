package com.douzi.dd.demo.CoordinatorLayout;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class FollowBottomBehavior extends CoordinatorLayout.Behavior<View> {

    public FollowBottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    boolean hasInitHeight = false;

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int translationY = dependency.getTop() + dependency.getHeight();
        child.setTranslationY(translationY);
        child.getLayoutParams().height = parent.getHeight() - translationY;
        child.requestLayout();
        return true;
    }

}
