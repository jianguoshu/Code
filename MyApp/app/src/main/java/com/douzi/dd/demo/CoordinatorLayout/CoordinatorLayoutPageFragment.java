package com.douzi.dd.demo.CoordinatorLayout;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.douzi.dd.R;

public class CoordinatorLayoutPageFragment extends Fragment {
    public static final String ARG_PAGE = "PAGE_NUM";
    private int mPage;

    private AppBarLayout mAppBarLayout;
    private WebView mWebView;
    private CollapsingToolbarLayout toolbarLayout;
    private int searchBoxHeight;

    public static CoordinatorLayoutPageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        CoordinatorLayoutPageFragment pageFragment = new CoordinatorLayoutPageFragment();
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
        View contentView = inflater.inflate(R.layout.fragment_page_coordinatorlayout, null);

        searchBoxHeight = getResources().getDimensionPixelSize(R.dimen.web_topbar_height);

        mAppBarLayout = contentView.findViewById(R.id.appbar);

        mWebView = contentView.findViewById(R.id.wb_content);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setVerticalScrollBarEnabled(true);
        mWebView.loadUrl("http:wap.baidu.com");

        toolbarLayout = contentView.findViewById(R.id.toolbar_layout);

        updateFullScreen(isFullScreen);

        return contentView;
    }

    boolean isFullScreen = true;

    void updateFullScreen(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
        if (toolbarLayout == null) {
            return;
        }
        if (isFullScreen) {
            toolbarLayout.setMinimumHeight(0);
        } else {
            toolbarLayout.setMinimumHeight(searchBoxHeight);
        }
    }

    void setExpanded(boolean expanded, boolean animate) {
        if (mAppBarLayout == null) {
            return;
        }
        mAppBarLayout.setExpanded(expanded, animate);
    }
}