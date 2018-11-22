package com.douzi.dd.base;

import android.view.View;

import com.douzi.dd.BaseActivity;

/**
 * Created by douzi on 2017/8/25.
 */

public class ViewWindow2 extends ViewWindow {

    public ViewWindow2(BaseActivity mContext) {
        super(mContext);
    }

    @Override
    public void display(View contentView, ViewWindow.WindowDecorView.LayoutParams params) {
        super.display(contentView, params);

        if (isImmersiveMode()) {
            mContentView.setSystemUiVisibility(mContext.getWindow().getDecorView().getSystemUiVisibility());
        }
    }
}
