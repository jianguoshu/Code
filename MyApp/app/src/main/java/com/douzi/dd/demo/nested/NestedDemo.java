package com.douzi.dd.demo.nested;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;
import com.douzi.dd.demo.CoordinatorLayout.CoordinatorLayoutActivity;
import com.douzi.dd.demo.CoordinatorLayout.CoordinatorLayoutListActivity;
import com.douzi.dd.demo.CoordinatorLayout.CoordinatorLayoutViewPagerActivity;
import com.douzi.dd.demo.CoordinatorLayout.CoordinatorLayoutViewPagerWebViewActivity;
import com.douzi.dd.demo.CoordinatorLayout.PaaActivity;
import com.douzi.dd.demo.CoordinatorLayout.PaaActivity2;

public class NestedDemo extends BaseActivity implements View.OnClickListener {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, NestedDemo.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_demo);
        this.findViewById(R.id.tv_WebView_ScrollView).setOnClickListener(this);
        this.findViewById(R.id.tv_WebView_ScrollView2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_WebView_ScrollView:
                WebViewNestedActivity.startAct(this, WebViewNestedActivity.Type.DEF);
                break;
            case R.id.tv_WebView_ScrollView2:
                WebViewNestedActivity.startAct(this, WebViewNestedActivity.Type.T1);
                break;
        }
    }
}
