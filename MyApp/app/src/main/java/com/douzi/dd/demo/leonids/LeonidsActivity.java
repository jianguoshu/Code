package com.douzi.dd.demo.leonids;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.View;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;
import com.plattysoft.leonids.ParticleSystem;

public class LeonidsActivity extends BaseActivity {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, LeonidsActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leonids);
        findViewById(R.id.tv_btn_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                praticleEffect1(R.drawable.sd);
                praticleEffect1(R.drawable.wz);
            }
        });
        findViewById(R.id.tv_btn_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                praticleEffect2(R.drawable.sd);
                praticleEffect2(R.drawable.wz);
            }
        });

        findViewById(R.id.tv_btn_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                praticleEffect3((int) (getWindow().getDecorView().getWidth() / 3.0) * 1, -100, R.drawable.sd);
                praticleEffect3((int) (getWindow().getDecorView().getWidth() / 3.0) * 1, -100, R.drawable.wz);
                praticleEffect3((int) (getWindow().getDecorView().getWidth() / 3.0) * 2, -100, R.drawable.sd);
                praticleEffect3((int) (getWindow().getDecorView().getWidth() / 3.0) * 2, -100, R.drawable.wz);
            }
        });
    }

    private void praticleEffect1(@DrawableRes int drawable) {
        new ParticleSystem(this, 50, drawable, 10000)
                .setSpeedModuleAndAngleRange(0.05f, 0.2f, 60, 120)
                .setAcceleration(0.00005f, 90)
                .setScaleRange(0.5f, 1.0f)
                .emit((int) (getWindow().getDecorView().getWidth() / 2.0), -100, 1, 10000);
    }

    private void praticleEffect2(@DrawableRes int drawable) {
        new ParticleSystem(this, 50, drawable, 10000)
                .setSpeedModuleAndAngleRange(0.05f, 0.2f, 0, 180)
                .setAcceleration(0.00005f, 90)
                .setScaleRange(0.5f, 1.0f)
                .emit((int) (getWindow().getDecorView().getWidth() / 2.0), -100, 1, 10000);
    }

    private void praticleEffect3(int x, int y, @DrawableRes int drawable) {
        new ParticleSystem(this, 50, drawable, 10000)
                .setSpeedModuleAndAngleRange(0.05f, 0.2f, 60, 120)
                .setAcceleration(0.00005f, 90)
                .setScaleRange(0.5f, 1.0f)
                .emit(x, y, 1, 10000);
    }
}
