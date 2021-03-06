package com.douzi.dd.demo.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView {

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
}
