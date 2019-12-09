package com.douzi.dd.demo.CoordinatorLayout;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

public class AlphaBehavior5 extends CoordinatorLayout.Behavior<View> {

    public AlphaBehavior5(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
        return behavior instanceof CusBottomSheetBehavior;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        CusBottomSheetBehavior behavior = (CusBottomSheetBehavior) ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
        child.setAlpha(1 - behavior.getCurSlide());
        return true;
    }
}
