package com.douzi.dd.demo.longclick;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by douzi on 2017/7/28.
 */

@SuppressLint("AppCompatCustomView")
public class ChildView extends TextView {
    public ChildView(Context context) {
        super(context);
    }

    public ChildView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
