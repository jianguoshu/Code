package com.douzi.dd.demo.media;

import android.support.annotation.IntDef;

@IntDef({ScaleMode.CENTER_CROP, ScaleMode.FIT_CENTER})
@interface ScaleMode {
    int CENTER_CROP = 0;
    int FIT_CENTER = 1;
}
