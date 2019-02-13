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

    private TextView btnFullScreen;
    private CoordinatorLayoutPageFragment coordinatorLayoutPageFragment;
    private CoordinatorLayoutPageFragmentCover coordinatorLayoutPageFragmentCover;

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

        ViewPager mViewPager = this.findViewById(R.id.viewpager);
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

        updateFullScreen(isFullScreen);
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
}
