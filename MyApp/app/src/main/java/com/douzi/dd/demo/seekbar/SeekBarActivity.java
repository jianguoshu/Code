package com.douzi.dd.demo.seekbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;
import com.douzi.dd.utils.DeviceUtil;

public class SeekBarActivity extends BaseActivity {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, SeekBarActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_bar);

        StepSeekBar seekBar = this.findViewById(R.id.seek_bar);
        seekBar.setProgressPointArr(DeviceUtil.dip2px(20), 0, 33, 66, 99);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setStepChangeListener(new OnStepChangeListener() {
            @Override
            public void onStepChanged(int step) {
                Log.i("onProgressChanged", "step = " + step);
            }
        });
    }
}
