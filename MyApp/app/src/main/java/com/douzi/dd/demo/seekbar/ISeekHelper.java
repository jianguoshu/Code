package com.douzi.dd.demo.seekbar;

import android.view.MotionEvent;

public interface ISeekHelper {
    void onViewWidthChanged(int width);

    int calProgress(int progressNew, int progressMax);

    int calPointByProgress(int progress, int progressMax, int def);

    int calProgressByEvent(MotionEvent event, int def);

    int calPointByEvent(MotionEvent event, int def);

    int calProgressByPoint(int point, int def);

    boolean isTouchInPoint(MotionEvent event);

    boolean isTouchInPointCur(MotionEvent event, int progressCur);
}
