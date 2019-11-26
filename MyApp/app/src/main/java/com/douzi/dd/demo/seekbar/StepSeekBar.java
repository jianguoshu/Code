package com.douzi.dd.demo.seekbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class StepSeekBar extends AppCompatSeekBar {

    private ISeekHelper helper;
    private OnStepChangeListener stepChangeListener;

    public void setProgressPointArr(int pointWidth, int... pointId) {
        setMax(pointId[pointId.length - 1]);
        helper = new SeekStepHelper(pointWidth, pointId);
    }

    public void setHelper(ISeekHelper helper) {
        this.helper = helper;
    }

    public void setStepChangeListener(OnStepChangeListener stepChangeListener) {
        this.stepChangeListener = stepChangeListener;
    }

    public StepSeekBar(Context context) {
        super(context);
        init();
    }

    public StepSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StepSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        super.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser && helper != null && stepChangeListener != null) {
                    int step = helper.getPointProgress(helper.getProgressPoint(progress, -1), -1);
                    if (step != -1) {
                        stepChangeListener.onStepChanged(step);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        helper.onViewWidthChanged(getWidth());
        setProgress(helper.calProgress(getProgress(), getMax()));
    }

    private boolean isTouchInPoint = true;
    private boolean isTouchInPointCur = true;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            isTouchInPoint = helper.isTouchInPoint(event);
            isTouchInPointCur = helper.isTouchInPointCur(event, getProgress());
        }

        if (MotionEvent.ACTION_UP == event.getAction() || MotionEvent.ACTION_CANCEL == event.getAction()) {
            if (isTouchInPointCur) {
                super.onTouchEvent(event);
                int progress = helper.calProgress(getProgress(), getMax());
                setProgress(progress);
            } else if (isTouchInPoint && helper.isTouchInPoint(event)) {
                int progress = helper.calProgressByEvent(event, getProgress());
                setProgress(progress);
            }
            isTouchInPoint = true;
            isTouchInPointCur = true;
            return true;
        }

        if (!(isTouchInPoint && isTouchInPointCur)) {
            return true;
        }

        return super.onTouchEvent(event);
    }


}
