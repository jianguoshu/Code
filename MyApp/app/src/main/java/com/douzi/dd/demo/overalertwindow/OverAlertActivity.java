package com.douzi.dd.demo.overalertwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.MyApplication;
import com.douzi.dd.R;
import com.douzi.dd.utils.DeviceUtil;

public class OverAlertActivity extends BaseActivity {

    private WindowManager mWindowManager;
    private View mContentView;
    private boolean isDisplaying;
    private WindowManager.LayoutParams layoutParams;

    int width = DeviceUtil.dip2px(100);
    int height = DeviceUtil.dip2px(100);
    private int positionX = 0;
    private int positionY = 0;
    private int positionXMin = 0;
    private int positionYMin = 0;
    private int positionXMax = 0;
    private int positionYMax = 0;
    private float touchXLast = 0;
    private float touchYLast = 0;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, OverAlertActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_alert);
        View btnTypeToast = this.findViewById(R.id.tv_type_toast);
        btnTypeToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayWindowToast();
            }
        });
        View btnTypeSysAlert = this.findViewById(R.id.tv_type_system_alert);
        btnTypeSysAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayWindowSysAlert();
            }
        });
    }

    private void displayWindowToast() {
        displayWindow("TYPE_TOAST", WindowManager.LayoutParams.TYPE_TOAST, 0, 0);
    }

    private void displayWindowSysAlert() {
        displayWindow("TYPE_SYSTEM_ALERT", WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, 100, 100);
    }

    private void displayWindow(String name, int type, int pX, int pY) {
        positionX = pX;
        positionY = pY;
        if (positionXMax == 0) {
            float screenWidthInPixels = DeviceUtil.getScreenWidthInPixels();
            positionXMax = (int) Math.max(screenWidthInPixels - width, 0);
        }
        if (positionYMax == 0) {
            float screenHeightPixels = DeviceUtil.getScreenHeightPixels();
            positionYMax = (int) Math.max(screenHeightPixels - height, 0);
        }

        Log.i("douzi", "positionXMax : " + positionXMax);
        Log.i("douzi", "positionYMax : " + positionYMax);

        mWindowManager = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.LEFT | Gravity.BOTTOM;
        layoutParams.format = PixelFormat.TRANSLUCENT; // 显示效果
        layoutParams.type = type;
        layoutParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.x = positionX;
        layoutParams.y = positionY;

        mContentView = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.over_asert_window, null);

        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float touchX = event.getRawX();
                float touchY = event.getRawY();
                Log.i("douzi", "event.getRawX() : " + touchX);
                Log.i("douzi", "event.getRawY() : " + touchY);

                if (touchXLast > 0 || touchYLast > 0) {
                    float dx = touchX - touchXLast;
                    float dy = touchY - touchYLast;
                    Log.i("douzi", "dx : " + dx);
                    Log.i("douzi", "dy : " + dy);
                    positionX += dx;
                    positionY += dy;
                    Log.i("douzi", "positionX : " + positionX);
                    Log.i("douzi", "positionY : " + positionY);
                    positionX = Math.min(Math.max(positionX, positionXMin), positionXMax);
                    positionY = Math.min(Math.max(positionY, positionYMin), positionYMax);
                    Log.i("douzi", "positionX : " + positionX);
                    Log.i("douzi", "positionY : " + positionY);
                    layoutParams.x = positionX;
                    layoutParams.y = positionY;
                    Log.i("douzi", "updateViewLayout("+positionX+", "+positionY+")");
                    mWindowManager.updateViewLayout(mContentView, layoutParams);
                }
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    touchXLast = 0;
                    touchYLast = 0;
                } else {
                    touchXLast = touchX;
                    touchYLast = touchY;
                }
                return false;
            }
        });

        TextView tvName = (TextView) mContentView.findViewById(R.id.tv_window_name);
        tvName.setText(name);

        View btnClose = mContentView.findViewById(R.id.tv_btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

        mWindowManager.addView(mContentView, layoutParams);

        isDisplaying = true;
    }

    public void hide() {
        if (isDisplaying) {
            if (mContentView != null && mContentView.getParent() != null) {
                mWindowManager.removeView(mContentView);
            }
            mWindowManager = null;
            mContentView = null;
            isDisplaying = false;
        }
    }
}
