package com.douzi.dd.demo.CoordinatorLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;

public class CoordinatorLayoutViewPagerActivity extends BaseActivity {
    private CollapsingToolbarLayout toolbarLayout;
    private View channelLayout;
    private int searchBoxHeight;
    private TextView btnFullScreen;
    private TextView btnCover;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, CoordinatorLayoutViewPagerActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_view_pager);

        searchBoxHeight = getResources().getDimensionPixelSize(R.dimen.web_topbar_height);

        ViewPager mViewPager = this.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), this));

        toolbarLayout = this.findViewById(R.id.toolbar_layout);
        channelLayout = this.findViewById(R.id.layout_search_result_channel);
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
}
