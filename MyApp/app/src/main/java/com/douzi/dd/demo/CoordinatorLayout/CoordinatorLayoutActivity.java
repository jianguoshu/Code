package com.douzi.dd.demo.CoordinatorLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;

public class CoordinatorLayoutActivity extends BaseActivity {

    private WebView mWebView;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, CoordinatorLayoutActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);

        mWebView = this.findViewById(R.id.wb_content);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setVerticalScrollBarEnabled(true);
        mWebView.loadUrl("http:wap.baidu.com");
    }
}
