package com.douzi.dd.demo.java;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;

public class JavaTestActivity extends BaseActivity {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, JavaTestActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_test);

        A a = new C();
        B b = (B) a;
        a.say();
        b.talk();
    }
}
