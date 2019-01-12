package com.douzi.dd.base;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.douzi.dd.base.RoundCornerViewOutlineProvider;

public class RoundCornerTextView extends TextView {
    public RoundCornerTextView(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new RoundCornerViewOutlineProvider(200));
        }
    }

    public RoundCornerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new RoundCornerViewOutlineProvider(200));
        }
    }

    public RoundCornerTextView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new RoundCornerViewOutlineProvider(200));
        }
    }
}
