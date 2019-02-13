package com.douzi.dd.demo.CoordinatorLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;

import java.util.logging.Logger;

public class CoordinatorLayoutViewPagerWebViewActivity extends BaseActivity {
    private CollapsingToolbarLayout toolbarLayout;
    private View channelLayout;
    private int searchBoxHeight;
    private TextView btnFullScreen;
    private TextView btnSwitch;
    private TextView btnScrollMode;

    boolean isViewPagerCanScroll = false;
    private boolean isHomePageDisplay = true;
    private CustomViewPager mViewPager;
    private AppBarLayout mAppBarLayout;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, CoordinatorLayoutViewPagerWebViewActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_view_pager_web_view);

        searchBoxHeight = getResources().getDimensionPixelSize(R.dimen.web_topbar_height);

        mAppBarLayout = this.findViewById(R.id.appbar);

        mViewPager = this.findViewById(R.id.viewpager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                mAppBarLayout.setExpanded(true, false);
            }

            @Override
            public void onPageSelected(int i) {
                updatePage(i == 0, false);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPager.setAdapter(new WebViewPagerAdapter(getSupportFragmentManager(), this));

        toolbarLayout = this.findViewById(R.id.toolbar_layout);
        channelLayout = this.findViewById(R.id.layout_search_result_channel);
        btnFullScreen = this.findViewById(R.id.btn_full_screen);
        btnFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFullScreen();
            }
        });

        btnSwitch = this.findViewById(R.id.btn_switch);
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePage(!isHomePageDisplay, true);
            }
        });

        btnScrollMode = this.findViewById(R.id.btn_switch_scroll);
        btnScrollMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchScrollMode();
            }
        });

        updateFullScreen(isFullScreen);
        updatePage(isHomePageDisplay, false);
        switchScrollMode();
    }

    boolean isFullScreen = true;

    private void switchFullScreen() {
        isFullScreen = !isFullScreen;
        updateFullScreen(isFullScreen);
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

    private void updatePage(boolean homePageDisplay, boolean byHand) {
        this.isHomePageDisplay = homePageDisplay;
        if (isHomePageDisplay) {
            channelLayout.setVisibility(View.VISIBLE);
            btnSwitch.setText("打开二级页");
            if (byHand) {
                mViewPager.setCurrentItem(0, false);
            }
        } else {
            channelLayout.setVisibility(View.GONE);
            btnSwitch.setText("回到首页");
            if (byHand) {
                mViewPager.setCurrentItem(1, false);
            }
        }
        mAppBarLayout.setExpanded(true, false);
    }

    private void switchScrollMode() {
        isViewPagerCanScroll = !isViewPagerCanScroll;
        mViewPager.setCanScroll(isViewPagerCanScroll);
        if (isViewPagerCanScroll) {
            btnScrollMode.setText("关闭滑动切换");
        } else {
            btnScrollMode.setText("打开滑动切换");
        }
    }
}
