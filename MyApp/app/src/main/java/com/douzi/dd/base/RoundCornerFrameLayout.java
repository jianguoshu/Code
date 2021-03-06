package com.douzi.dd.base;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.douzi.dd.utils.DeviceUtil;

public class RoundCornerFrameLayout extends FrameLayout {
    public RoundCornerFrameLayout(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new RoundCornerViewOutlineProvider(200));
            setClipToOutline(true);
        }
    }

    public RoundCornerFrameLayout(Context context,  AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new RoundCornerViewOutlineProvider(200));
            setClipToOutline(true);
        }
    }

    public RoundCornerFrameLayout(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new RoundCornerViewOutlineProvider(200));
            setClipToOutline(true);
        }
    }
}
