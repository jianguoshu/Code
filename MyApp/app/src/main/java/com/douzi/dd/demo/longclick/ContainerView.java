package com.douzi.dd.demo.longclick;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by douzi on 2017/7/28.
 */

public class ContainerView extends FrameLayout {
    public ContainerView(@NonNull Context context) {
        super(context);
    }

    public ContainerView(@NonNull Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public ContainerView(@NonNull Context context,  AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ContainerView(@NonNull Context context, AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
