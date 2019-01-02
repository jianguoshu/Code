package com.douzi.dd.demo.camera;

import android.hardware.Camera;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.douzi.dd.utils.DeviceUtil;
import com.douzi.dd.utils.ListUtils;

import java.util.HashMap;
import java.util.List;

public class CameraConfig {

    @IntDef({Degree.INVALID, Degree.D_0, Degree.D_90, Degree.D_180, Degree.D_270})
    @interface Degree {
        int INVALID = -1;
        int D_0 = 0;
        int D_90 = 90;
        int D_180 = 180;
        int D_270 = 270;
    }

    HashMap<Integer, Integer> rotationMap = new HashMap<>();
    HashMap<Integer, Integer> orientationMap = new HashMap<>();
    HashMap<Integer, PreviewSize> previewSizeMap = new HashMap<>();

    CameraConfig() {
        initRotation();
        initOrientation();
    }

    @Degree
    int getRotation(@CameraId int id) {
        Integer rotation = rotationMap.get(id);
        return rotation != null ? rotation : Degree.D_0;
    }

    @Degree
    int getOrientation(@CameraId int id) {
        Integer orientation = orientationMap.get(id);
        return orientation != null ? orientation : Degree.INVALID;
    }

    @Nullable
    PreviewSize getPreviewSize(@CameraId int id) {
        return previewSizeMap.get(id);
    }

    private void initOrientation() {
        int rotateDegree = Degree.INVALID;
        if (!DeviceUtil.isXT800() && !DeviceUtil.isHtcHero() && !DeviceUtil.isS5360()) {
            rotateDegree = Degree.D_90;
            if (DeviceUtil.isMeizuM9()) {
                rotateDegree = Degree.D_180;
            }
        }
        orientationMap.put(CameraId.FRONT, rotateDegree);
        orientationMap.put(CameraId.BACK, rotateDegree);

        if (DeviceUtil.isNexus6p()) {
            orientationMap.put(CameraId.FRONT, Degree.D_270);
        }
    }

    private void initRotation() {
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            rotationMap.put(i, cameraInfo.orientation);
        }
    }

    void initSize(@NonNull Camera camera, @CameraId int id, @ScaleMode int mode, int width, int height) {
        if (getPreviewSize(id) != null || width <= 0 || height <= 0) {
            return;
        }
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
        Camera.Size size = null;
        if (ListUtils.isNotEmpty(sizeList)) {
            size = sizeList.get(1);
        }
        if (size != null) {
            int cameraWidth = size.width;
            int cameraHeight = size.height;
            int previewWidth;
            int previewHeight;
            switch (mode) {
                case ScaleMode.FIT_WIDTH:
                    if (getOrientation(id) == Degree.D_90 || getOrientation(id) == Degree.D_270) {
                        previewWidth = width;
                        previewHeight = (int) (1.0f * cameraWidth / cameraHeight * width);
                    } else {
                        previewWidth = width;
                        previewHeight = (int) (1.0f * cameraHeight / cameraWidth * width);
                    }
                    break;
                case ScaleMode.FIT_HEIGHT:
                    if (getOrientation(id) == Degree.D_90 || getOrientation(id) == Degree.D_270) {
                        previewWidth = (int) (1.0f * cameraHeight / cameraWidth * height);
                        previewHeight = height;
                    } else {
                        previewWidth = (int) (1.0f * cameraWidth / cameraHeight * height);
                        previewHeight = height;
                    }
                    break;
                case ScaleMode.CROP:
                default:
                    float previewRatio = 1.0f * width / height;
                    float cameraRatio;
                    if (getOrientation(id) == Degree.D_90 || getOrientation(id) == Degree.D_270) {
                        cameraRatio = 1.0f * cameraHeight / cameraWidth;
                    } else {
                        cameraRatio = 1.0f * cameraWidth / cameraHeight;
                    }
                    if (cameraRatio > previewRatio) {
                        initSize(camera, id, ScaleMode.FIT_HEIGHT, width, height);
                    } else {
                        initSize(camera, id, ScaleMode.FIT_WIDTH, width, height);
                    }
                    return;
            }

            previewSizeMap.put(id, new PreviewSize(cameraWidth, cameraHeight, previewWidth, previewHeight));
        }
    }
}

