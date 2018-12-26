package com.douzi.dd.demo.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.douzi.dd.utils.DeviceUtil;
import com.douzi.dd.utils.ListUtils;

import java.util.HashMap;
import java.util.List;

public class CameraPreview extends SurfaceView {

    @IntDef({ScaleMode.CROP, ScaleMode.FIT_WIDTH, ScaleMode.FIT_HEIGHT})
    @interface ScaleMode {
        int CROP = 0;
        int FIT_WIDTH = 1;
        int FIT_HEIGHT = 2;
    }

    @IntDef({CameraId.FRONT, CameraId.BACK})
    @interface CameraId {
        int FRONT = Camera.CameraInfo.CAMERA_FACING_FRONT;
        int BACK = Camera.CameraInfo.CAMERA_FACING_BACK;
    }

    private @Nullable
    Camera mCamera;
    private @NonNull
    CameraConfig mCameraConfig = new CameraConfig();
    private @CameraId
    int cameraId;
    private @ScaleMode
    int scaleMode;
    private boolean isPreviewing = false;

    public CameraPreview(Context context) {
        super(context);
        init();
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        cameraId = CameraId.FRONT;
        scaleMode = ScaleMode.FIT_WIDTH;
        getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                startPreview();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                releaseCamera();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        PreviewSize previewSize = mCameraConfig.getPreviewSize(cameraId);
        if (previewSize != null) {
            setMeasuredDimension(previewSize.viewWidth, previewSize.viewHeight);
        }
    }

    void startPreview() {
        Activity activity = (Activity) getContext();
        int hasReadExternalStoragePermission = ContextCompat.checkSelfPermission(activity.getApplication(), Manifest.permission.CAMERA);
        if (hasReadExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1);
            return;
        }

        if (isPreviewing) {
            return;
        }

        initCamera(cameraId);
        if (mCamera != null) {
            mCamera.startPreview();
            isPreviewing = true;
        }
    }

    public void switchCamera() {
        releaseCamera();
        switchCameraId();
        startPreview();
    }

    private void switchCameraId() {
        switch (cameraId) {
            case CameraId.BACK:
                cameraId = CameraId.FRONT;
                break;
            case CameraId.FRONT:
                cameraId = CameraId.BACK;
                break;
        }
    }

    private void initCamera(@CameraId int id) {
        try {
            if (mCamera == null) {
                mCamera = Camera.open(id);
                if (mCamera != null) {
                    @CameraConfig.Degree int orientation = mCameraConfig.getOrientation(id);
                    if (orientation != CameraConfig.Degree.INVALID) {
                        mCamera.setDisplayOrientation(orientation);
                    }
                    Camera.Parameters parameters = mCamera.getParameters();
                    parameters.setPreviewFormat(ImageFormat.NV21);
                    parameters.setRotation(mCameraConfig.getRotation(id));
                    parameters.setFocusMode(Camera.Parameters.FLASH_MODE_AUTO);
                    PreviewSize previewSize = mCameraConfig.getPreviewSize(id);
                    if (previewSize != null) {
                        parameters.setPreviewSize(previewSize.cameraWidth, previewSize.cameraHeight);
                    } else {
                        mCameraConfig.initSize(mCamera, id, scaleMode, getMeasuredWidth(), getMeasuredHeight());
                        previewSize = mCameraConfig.getPreviewSize(id);
                        if (previewSize != null) {
                            parameters.setPreviewSize(previewSize.cameraWidth, previewSize.cameraHeight);
                        }
                        requestLayout();
                    }
                    mCamera.setParameters(parameters);
                    mCamera.setPreviewDisplay(this.getHolder());
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            mCamera = null;
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        isPreviewing = false;
    }

    private static class CameraConfig {

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

        @Nullable PreviewSize getPreviewSize(@CameraId int id) {
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

    private static class PreviewSize {
        int cameraWidth;
        int cameraHeight;
        int viewWidth;
        int viewHeight;

        PreviewSize(int cameraWidth, int cameraHeight, int viewWidth, int viewHeight) {
            this.cameraWidth = cameraWidth;
            this.cameraHeight = cameraHeight;
            this.viewWidth = viewWidth;
            this.viewHeight = viewHeight;
        }
    }
}
