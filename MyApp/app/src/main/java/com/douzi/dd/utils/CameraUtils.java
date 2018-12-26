package com.douzi.dd.utils;

import android.hardware.Camera;
import android.os.Build;

public class CameraUtils {
    public static int getNumberOfCameras() {
        if (Build.VERSION.SDK_INT >= 10) {
            return Camera.getNumberOfCameras();
        } else {
            return 1;
        }
    }
}
