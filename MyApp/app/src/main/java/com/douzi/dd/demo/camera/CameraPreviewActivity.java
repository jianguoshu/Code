package com.douzi.dd.demo.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CameraPreviewActivity extends BaseActivity {

    private CameraPreview2 cameraPreview;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, CameraPreviewActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);

        cameraPreview = this.findViewById(R.id.camera_preview);
        this.findViewById(R.id.btn_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraPreview.switchCamera();
                Intent intent = new Intent();
                try {
                    intent.setData(Uri.parse("sogousearch://extension4result?query=" + URLEncoder.encode("刘虎", "GBK")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                CameraPreviewActivity.this.startActivity(intent);
            }
        });
        this.findViewById(R.id.btn_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraPreview.forceLayout();
            }
        });

        final ImageView captureDisplay = this.findViewById(R.id.iv_capture_display);
        this.findViewById(R.id.btn_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureDisplay.setImageBitmap(cameraPreview.capture());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (permissions[0].equals(Manifest.permission.CAMERA) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraPreview.startPreview();
            }
        }
    }
}
