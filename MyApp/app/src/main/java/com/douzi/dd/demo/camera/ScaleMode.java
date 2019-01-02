package com.douzi.dd.demo.camera;

import android.support.annotation.IntDef;

@IntDef({ScaleMode.CROP, ScaleMode.FIT_WIDTH, ScaleMode.FIT_HEIGHT})
@interface ScaleMode {
    int CROP = 0;
    int FIT_WIDTH = 1;
    int FIT_HEIGHT = 2;
}
