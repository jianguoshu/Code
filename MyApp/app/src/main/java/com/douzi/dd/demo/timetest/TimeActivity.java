package com.douzi.dd.demo.timetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import com.douzi.dd.R;

public class TimeActivity extends Activity {
    Handler mHandler = new Handler();

    public static void startAct(Context context) {
        Intent intent = new Intent(context, TimeActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_test);
        long timeCur = SystemClock.uptimeMillis();
        for (int i = 0; i < 120; i++) {
            final int finalI = i;
            mHandler.postAtTime(new Runnable() {
                @Override
                public void run() {
                    Log.i(TimeActivity.class.getSimpleName(), finalI + "");
                }
            }, TimeActivity.this, 5 * 1000 * i + timeCur);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(TimeActivity.this);
    }
}
