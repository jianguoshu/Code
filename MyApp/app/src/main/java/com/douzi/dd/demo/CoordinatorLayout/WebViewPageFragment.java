package com.douzi.dd.demo.CoordinatorLayout;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.douzi.dd.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.OVER_SCROLL_NEVER;

public class WebViewPageFragment extends Fragment {
    public static final String ARG_PAGE = "PAGE_NUM";
    private int mPage;

    public static WebViewPageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        WebViewPageFragment pageFragment = new WebViewPageFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_page_webview, null);

        WebView mWebView = contentView.findViewById(R.id.webview);
        initializeOptions(mWebView, mWebView.getSettings());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.loadUrl("http://www.sogou.com");

        return contentView;
    }

    protected void initializeOptions(WebView mWebView, WebSettings settings) {
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