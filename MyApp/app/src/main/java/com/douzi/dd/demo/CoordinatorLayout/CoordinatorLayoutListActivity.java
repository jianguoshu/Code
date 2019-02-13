package com.douzi.dd.demo.CoordinatorLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;

public class CoordinatorLayoutListActivity extends BaseActivity {

    boolean isFullScreen = true;
    boolean isViewPagerCanScroll = false;

    private TextView btnFullScreen;
    private TextView btnSwitch;
    private TextView btnScrollMode;
    private CoordinatorLayoutPageFragment coordinatorLayoutPageFragment;
    private CoordinatorLayoutPageFragmentCover coordinatorLayoutPageFragmentCover;
    private boolean isHomePageDisplay = true;
    private CustomViewPager mViewPager;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, CoordinatorLayoutListActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_list);
        coordinatorLayoutPageFragment = CoordinatorLayoutPageFragment.newInstance(0);
        coordinatorLayoutPageFragmentCover = CoordinatorLayoutPageFragmentCover.newInstance(1);

        mViewPager = this.findViewById(R.id.viewpager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                setExpanded(true, false);
            }

            @Override
            public void onPageSelected(int i) {
                updatePage(i == 0, false);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i) {
                    case 0:
                        return coordinatorLayoutPageFragment;
                    case 1:
                        return coordinatorLayoutPageFragmentCover;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

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

    private void switchFullScreen() {
        isFullScreen = !isFullScreen;
        updateFullScreen(isFullScreen);
    }

    private void updateFullScreen(boolean isFullScreen) {
        if (isFullScreen) {
            btnFullScreen.setText("关闭全屏");
        } else {
            btnFullScreen.setText("打开全屏");
        }
        coordinatorLayoutPageFragment.updateFullScreen(isFullScreen);
        coordinatorLayoutPageFragmentCover.updateFullScreen(isFullScreen);
    }

    private void updatePage(boolean homePageDisplay, boolean byHand) {
        this.isHomePageDisplay = homePageDisplay;
        if (isHomePageDisplay) {
            btnSwitch.setText("打开二级页");
            if (byHand) {
                mViewPager.setCurrentItem(0, false);
            }
        } else {
            btnSwitch.setText("回到首页");
            if (byHand) {
                mViewPager.setCurrentItem(1, false);
            }
        }
        setExpanded(true, false);
    }

    private void setExpanded(boolean expanded, boolean animate) {
        coordinatorLayoutPageFragment.setExpanded(expanded, animate);
        coordinatorLayoutPageFragmentCover.setExpanded(expanded, animate);
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
