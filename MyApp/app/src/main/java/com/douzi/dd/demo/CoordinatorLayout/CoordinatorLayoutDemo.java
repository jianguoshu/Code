package com.douzi.dd.demo.CoordinatorLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;
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

public class CoordinatorLayoutDemo extends BaseActivity implements View.OnClickListener {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, CoordinatorLayoutDemo.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_demo);

        this.findViewById(R.id.tv_CoordinatorLayoutActivity).setOnClickListener(this);
        this.findViewById(R.id.tv_CoordinatorLayoutActivity2).setOnClickListener(this);
        this.findViewById(R.id.tv_CoordinatorLayoutActivity5).setOnClickListener(this);
        this.findViewById(R.id.tv_CoordinatorLayoutActivity6).setOnClickListener(this);
        this.findViewById(R.id.tv_CoordinatorLayoutViewPagerActivity).setOnClickListener(this);
        this.findViewById(R.id.tv_CoordinatorLayoutViewPagerWebViewActivity).setOnClickListener(this);
        this.findViewById(R.id.tv_CoordinatorLayoutListActivity).setOnClickListener(this);
        this.findViewById(R.id.tv_PaaActivity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_CoordinatorLayoutActivity:
                CoordinatorLayoutActivity.startAct(this, CoordinatorLayoutActivity.Type.DEF);
                break;
            case R.id.tv_CoordinatorLayoutActivity2:
                CoordinatorLayoutActivity.startAct(this, CoordinatorLayoutActivity.Type.T1);
                break;
            case R.id.tv_CoordinatorLayoutActivity5:
                CoordinatorLayoutActivity.startAct(this, CoordinatorLayoutActivity.Type.T2);
                break;
            case R.id.tv_CoordinatorLayoutActivity6:
                CoordinatorLayoutActivity.startAct(this, CoordinatorLayoutActivity.Type.T3);
                break;
            case R.id.tv_CoordinatorLayoutViewPagerActivity:
                CoordinatorLayoutViewPagerActivity.startAct(this);
                break;
            case R.id.tv_CoordinatorLayoutViewPagerWebViewActivity:
                CoordinatorLayoutViewPagerWebViewActivity.startAct(this);
                break;
            case R.id.tv_CoordinatorLayoutListActivity:
                CoordinatorLayoutListActivity.startAct(this);
                break;
            case R.id.tv_PaaActivity:
                PaaActivity.startAct(this);
                break;
        }
    }
}
