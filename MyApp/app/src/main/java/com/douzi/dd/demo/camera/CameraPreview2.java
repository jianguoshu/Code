package com.douzi.dd.demo.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.TextureView;

public class CameraPreview2 extends TextureView {

    private @Nullable
    Camera mCamera;
    private @NonNull
    CameraConfig mCameraConfig = new CameraConfig();
    private @CameraId
    int cameraId;
    private @ScaleMode
    int scaleMode;
    private boolean isPreviewing = false;

    public CameraPreview2(Context context) {
        super(context);
        init();
    }

    public CameraPreview2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraPreview2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        cameraId = CameraId.FRONT;
        scaleMode = ScaleMode.FIT_WIDTH;
        setSurfaceTextureListener(new SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                startPreview();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                releaseCamera();
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

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
                    mCamera.setPreviewTexture(getSurfaceTexture());
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

    public @Nullable
    Bitmap capture() {
        Bitmap bitmap = null;
        try {
            bitmap = getBitmap();
            int width = getWidth();
            int height = getHeight();
            int widthBm = bitmap.getWidth();
            int heightBm = bitmap.getHeight();
            if (widthBm != width || heightBm != height) {
                float scaleWidth = ((float) width) / widthBm;
                float scaleHeight = ((float) height) / heightBm;
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, widthBm, heightBm, matrix, true);
            }

            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            int widthVisible = rect.right - rect.left;
            int heightVisible = rect.bottom - rect.top;
            int startX = 0;
            int startY = 0;
            if (widthVisible < width) {
                startX = (int) (1.0f * (width - widthVisible) / 2);
            }

            if (heightVisible < height) {
                startY = (int) (1.0f * (height - heightVisible) / 2);
            }

            return Bitmap.createBitmap(bitmap, startX, startY, widthVisible, heightVisible);
        } catch (Throwable e) {
            e.printStackTrace();
            return bitmap;
        }
    }
}
