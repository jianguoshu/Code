package com.douzi.dd.demo.seekbar;

import android.util.SparseIntArray;
import android.view.MotionEvent;

public class SeekStepHelper implements ISeekHelper{
    private int[] progressPointArr;
    private SparseIntArray pointSA = new SparseIntArray();
    private int pointWidth;
    private int viewWidth;

    public SeekStepHelper(int pointWidth, int... pointArr) {
        this.progressPointArr = pointArr;
        this.pointWidth = pointWidth;
        pointSA.clear();
        for (int i = 0; i < progressPointArr.length; i++) {
            pointSA.put(progressPointArr[i], i);
        }

    }

    @Override
    public void onViewWidthChanged(int width) {
        this.viewWidth = width;
    }

    @Override
    public int calProgress(int progressNew, int progressMax) {
        if (viewWidth <= 0) {
            return progressNew;
        }

        int point = calPointByProgress(progressNew, progressMax, -1);
        return getPointProgress(point, progressNew);
    }

    @Override
    public int calPointByProgress(int progress, int progressMax, int def) {
        int halfWidth = (int) (1.0f * progressMax / (progressPointArr.length - 1) / 2);
        for (int i = 0; i < progressPointArr.length; i++) {
            int v = (int) (1.0f * i * progressMax / (progressPointArr.length - 1));
            if (Math.abs(progress - v) <= halfWidth) {
                return i;
            }
        }
        return def;
    }

    @Override
    public int calProgressByEvent(MotionEvent event, int def) {
        if (viewWidth <= 0) {
            return def;
        }

        return getPointProgress(calPointByEvent(event, -1), def);
    }

    @Override
    public int calPointByEvent(MotionEvent event, int def) {
        if (viewWidth <= 0) {
            return def;
        }

        float x = event.getX();
        int dividerWidth = (int) (1.0f * (viewWidth - progressPointArr.length * pointWidth) / (progressPointArr.length - 1));
        for (int i = 0; i < progressPointArr.length; i++) {
            if (x >= i * (dividerWidth + pointWidth) && x <= i * dividerWidth + (i + 1) * pointWidth) {
                return i;
            }
        }
        return def;
    }

    @Override
    public int getPointProgress(int point, int def) {
        if (isIndexValid(point)) {
            return progressPointArr[point];
        }
        return def;
    }

    @Override
    public int getProgressPoint(int progress, int def) {
        int point = pointSA.get(progress, -1);
        if (isIndexValid(point)) {
            return point;
        }
        return def;
    }

    @Override
    public boolean isTouchInPoint(MotionEvent event) {
        if (viewWidth <= 0) {
            return true;
        }
        float x = event.getX();
        int dividerWidth = (int) (1.0f * (viewWidth - progressPointArr.length * pointWidth) / (progressPointArr.length - 1));
        for (int i = 0; i < progressPointArr.length; i++) {
            if (x >= i * (dividerWidth + pointWidth) && x <= i * dividerWidth + (i + 1) * pointWidth) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isTouchInPointCur(MotionEvent event, int progressCur) {
        int pointCur = pointSA.get(progressCur, -1);
        if (viewWidth <= 0 || !isIndexValid(pointCur)) {
            return true;
        }
        float x = event.getX();
        int dividerWidth = (int) (1.0f * (viewWidth - progressPointArr.length * pointWidth) / (progressPointArr.length - 1));
        if (x >= pointCur * (dividerWidth + pointWidth) && x <= pointCur * dividerWidth + (pointCur + 1) * pointWidth) {
            return true;
        }

        return false;
    }

    private boolean isIndexValid(int index) {
        return index >= 0 && index < progressPointArr.length;
    }
}
