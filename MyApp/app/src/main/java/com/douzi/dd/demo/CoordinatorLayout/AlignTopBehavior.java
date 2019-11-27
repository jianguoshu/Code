package com.douzi.dd.demo.CoordinatorLayout;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

public class AlignTopBehavior extends CoordinatorLayout.Behavior<View> {

    public AlignTopBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int translationY = dependency.getTop();
        child.setTranslationY(translationY);
        child.getLayoutParams().height = parent.getHeight() - translationY;
        child.requestLayout();
        return true;
    }

}
