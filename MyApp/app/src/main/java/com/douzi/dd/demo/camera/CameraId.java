package com.douzi.dd.demo.camera;

import android.hardware.Camera;
import android.support.annotation.IntDef;

@IntDef({CameraId.FRONT, CameraId.BACK})
@interface CameraId {
    int FRONT = Camera.CameraInfo.CAMERA_FACING_FRONT;
    int BACK = Camera.CameraInfo.CAMERA_FACING_BACK;
}