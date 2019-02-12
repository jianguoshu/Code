package com.douzi.dd;

import android.os.Bundle;
import android.view.View;

import com.douzi.dd.demo.CoordinatorLayout.CoordinatorLayoutActivity;
import com.douzi.dd.demo.GridLayoutTest.GridLayoutActivity;
import com.douzi.dd.demo.anr.ANRActivity;
import com.douzi.dd.demo.camera.CameraPreviewActivity;
import com.douzi.dd.demo.leonids.LeonidsActivity;
import com.douzi.dd.demo.maptest.HashBiMapActivity;
import com.douzi.dd.demo.media.VideoViewActivity;
import com.douzi.dd.demo.overalertwindow.OverAlertActivity;
import com.douzi.dd.demo.recyclerview.RecyclerViewActivity;
import com.douzi.dd.demo.servicetest.ServiceLifecycleActivity;
import com.douzi.dd.demo.splittouch.SplitTouchActivity;
import com.douzi.dd.demo.timetest.TimeActivity;
import com.douzi.dd.demo.toast.ToastActivity;
import com.douzi.dd.demo.xy.XYActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.tv_btn_xy).setOnClickListener(this);
        this.findViewById(R.id.tv_btn_recycler).setOnClickListener(this);
        this.findViewById(R.id.tv_btn_toast).setOnClickListener(this);
        this.findViewById(R.id.tv_btn_split_touch).setOnClickListener(this);
        this.findViewById(R.id.tv_btn_service_lifecycle).setOnClickListener(this);
        this.findViewById(R.id.tv_btn_gridlayout).setOnClickListener(this);
        this.findViewById(R.id.tv_btn_over_alert).setOnClickListener(this);
        this.findViewById(R.id.tv_btn_time_test).setOnClickListener(this);
        this.findViewById(R.id.tv_btn_anr_test).setOnClickListener(this);
        this.findViewById(R.id.tv_hashbimap_test).setOnClickListener(this);
        this.findViewById(R.id.tv_LeonidsActivity).setOnClickListener(this);
        this.findViewById(R.id.tv_VideoViewActivity).setOnClickListener(this);
        this.findViewById(R.id.tv_CameraPreviewActivity).setOnClickListener(this);
        this.findViewById(R.id.tv_CoordinatorLayoutActivity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_btn_xy:
                XYActivity.startAct(this);
                break;
            case R.id.tv_btn_recycler:
                RecyclerViewActivity.startAct(this);
                break;
            case R.id.tv_btn_toast:
                ToastActivity.startAct(this);
                break;
            case R.id.tv_btn_split_touch:
                SplitTouchActivity.startAct(this);
                break;
            case R.id.tv_btn_service_lifecycle:
                ServiceLifecycleActivity.startAct(this);
                break;
            case R.id.tv_btn_gridlayout:
                GridLayoutActivity.startAct(this);
                break;
            case R.id.tv_btn_over_alert:
                OverAlertActivity.startAct(this);
                break;
            case R.id.tv_btn_time_test:
                TimeActivity.startAct(this);
                break;
            case R.id.tv_btn_anr_test:
                ANRActivity.startAct(this);
                break;
            case R.id.tv_hashbimap_test:
                HashBiMapActivity.startAct(this);
                break;
            case R.id.tv_LeonidsActivity:
                LeonidsActivity.startAct(this);
                break;
            case R.id.tv_VideoViewActivity:
                VideoViewActivity.startAct(this);
                break;
            case R.id.tv_CameraPreviewActivity:
                CameraPreviewActivity.startAct(this);
                break;
            case R.id.tv_CoordinatorLayoutActivity:
                CoordinatorLayoutActivity.startAct(this);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
