package com.douzi.dd.demo.CoordinatorLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.OVER_SCROLL_NEVER;

public class CoordinatorLayoutActivity extends BaseActivity {

    private WebView mWebView;
    private CollapsingToolbarLayout toolbarLayout;
    private View channelLayout;
    private int searchBoxHeight;
    private TextView btnFullScreen;
    private TextView btnCover;
    private RecyclerView mListChannel;

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

        searchBoxHeight = getResources().getDimensionPixelSize(R.dimen.web_topbar_height);

        mWebView = this.findViewById(R.id.wb_content);
        initializeOptions(mWebView.getSettings());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
        mWebView.loadUrl("http://wap.sogou.com");

        toolbarLayout = this.findViewById(R.id.toolbar_layout);
        channelLayout = this.findViewById(R.id.layout_search_result_channel);
        mListChannel = (RecyclerView) findViewById(R.id.list_search_result_channel);
        mListChannel.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<String> channelList = new ArrayList<>();
        channelList.add("网页");
        channelList.add("小说");
        channelList.add("新闻");
        channelList.add("微信");
        channelList.add("网址");
        channelList.add("应用");
        channelList.add("图片");
        channelList.add("游戏");
        channelList.add("购物");
        channelList.add("视频");
        channelList.add("问问");
        channelList.add("百科");
        channelList.add("地图");
        channelList.add("音乐");
        channelList.add("本地");
        channelList.add("知乎");
        channelList.add("资源");
        channelList.add("表情");
        channelList.add("明医");
        channelList.add("英文");
        channelList.add("学术");
        channelList.add("词典");
        channelList.add("漫画");
        channelList.add("翻译");
        channelList.add("关注");
        channelList.add("答题");
        channelList.add("添加");
        ChannelAdapter mListChannelAdapter = new ChannelAdapter(this, channelList);
        mListChannel.setAdapter(mListChannelAdapter);
        btnFullScreen = this.findViewById(R.id.btn_full_screen);
        btnFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFullScreen();
            }
        });

        btnCover = this.findViewById(R.id.btn_cover);
        btnCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCover();
            }
        });

        updateFullScreen(isFullScreen);
        updateCover(isCoverDisplay);
    }

    boolean isFullScreen = true;
    boolean isCoverDisplay = false;

    private void switchFullScreen() {
        isFullScreen = !isFullScreen;
        updateFullScreen(isFullScreen);
    }

    private void switchCover() {
        isCoverDisplay = !isCoverDisplay;
        updateCover(isCoverDisplay);
    }

    private void updateFullScreen(boolean isFullScreen) {
        if (isFullScreen) {
            toolbarLayout.setMinimumHeight(0);
            btnFullScreen.setText("关闭全屏");
        } else {
            toolbarLayout.setMinimumHeight(searchBoxHeight);
            btnFullScreen.setText("打开全屏");
        }
    }

    private void updateCover(boolean isCoverDisplay) {
        if (isCoverDisplay) {
            channelLayout.setVisibility(View.GONE);
            btnCover.setText("关闭浮层");
        } else {
            channelLayout.setVisibility(View.VISIBLE);
            btnCover.setText("打开浮层");
        }
    }

    protected void initializeOptions(WebSettings settings) {
        try {
            // User settings
            settings.setJavaScriptEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);

            //settings.setAllowFileAccessFromFileURLs(true);
            //settings.setAllowUniversalAccessFromFileURLs(true);

//            if (PreferenceFactory.getSettingPref().isNeedShengLiuLiang()) {
//                settings.setLoadsImagesAutomatically(false);
//            } else {
            settings.setLoadsImagesAutomatically(true);
//            }

            // 设置加载进来的页面自适应手机屏幕【start】
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            // 设置加载进来的页面自适应手机屏幕【end】

            settings.setSaveFormData(true);

            /**
             * deprecated in API level 18
             */
//            if (android.os.Build.VERSION.SDK_INT < 18) {
//                settings.setSavePassword(true); // deprecated in API level 18
//                settings.setRenderPriority(WebSettings.RenderPriority.HIGH); // deprecated in API level 18
//                settings.setPluginState(WebSettings.PluginState.ON_DEMAND); // deprecated in API level 18
//            }

            /**
             * deprecated in API level 19
             */
            if (android.os.Build.VERSION.SDK_INT < 19) {
                settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM); // deprecated in API level 19
            }

            /**
             * 设为false时，webView 会自己load这个url.
             * 默认是false， 如果需要在外部设置true
             */
            settings.setSupportMultipleWindows(false);

            CookieManager.getInstance().setAcceptCookie(true);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
            }

            // 支持页面缩放
            settings.setSupportZoom(false);
            settings.setBuiltInZoomControls(false);

            // 不显示缩放按钮
            settings.setDisplayZoomControls(false);

            mWebView.setLongClickable(true);
            mWebView.setScrollbarFadingEnabled(true);
            mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            mWebView.setDrawingCacheEnabled(true);

            mWebView.setOverScrollMode(OVER_SCROLL_NEVER);

            /**
             * http://stackoverflow.com/questions/9128952/caching-in-android-webview
             *
             * Don't use these:
             *  viewer.getSettings().setAppCacheMaxSize(1024*1024*8);
             *  viewer.getSettings().setAppCachePath("/data/data/com.your.package.appname/cache"‌​);
             *  viewer.getSettings().setAppCacheEnabled(true);
             * These have nothing to do with the default webview internal cache. Appcache is an entirely different feature mean to make you able to run the website w/o an internet connection. It does not work that great and probably you do not want to use it.
             * With setting this: viewer.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT) is enough.
             */
            /*settings.setAppCacheEnabled(true);
            String appCacheDir = getContext().getDir("cache", Context.MODE_PRIVATE).getPath();
            settings.setAppCachePath(appCacheDir);
            settings.setAppCacheMaxSize(1024 * 1024 * 8); // deprecated in API level 18
            */
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);

            // 开启 DOM storage API 功能
            settings.setDomStorageEnabled(true);

            settings.setAllowFileAccess(true);

            /**
             * 修改漫画功能https和http混合图片不显示问题
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
