package com.douzi.dd.demo.media;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.VideoView;

import com.douzi.dd.BaseActivity;

public class FitHeightVideoView extends VideoView {

    private float mVideoWidth;
    private float mVideoHeight;
    private int mHeight;

    public void setVideoWidth(int videoWidth) {
        this.mVideoWidth = videoWidth;
    }

    public void setVideoHeight(int videoHeight) {
        this.mVideoHeight = videoHeight;
    }

    public FitHeightVideoView(Context context) {
        super(context);
    }

    public FitHeightVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FitHeightVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FitHeightVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeight == 0) {
//            mHeight = getMeasuredHeight();
            Rect rect = new Rect();
            getWindowVisibleDisplayFrame(rect);
            mHeight = ((BaseActivity)getContext()).getWindow().getDecorView().getHeight();
        }
        if (mVideoWidth == 0 || mVideoHeight == 0) {
            return;
        }

        setMeasuredDimension((int) (mHeight / mVideoHeight * mVideoWidth), mHeight);
    }
}
