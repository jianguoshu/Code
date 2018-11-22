package com.douzi.dd.demo.anr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.util.Log;
import android.view.View;

import com.douzi.dd.R;
import com.douzi.dd.utils.FileUtil;

public class ANRActivity extends Activity {

    private FileObserver mFileObserver2;
    private FileObserver mFileObserver;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, ANRActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);

        String pathname = getFilesDir() + "/test";
        final String filename = pathname + "testfile";

        mFileObserver = new FileObserver(filename) {
            @Override
            public void onEvent(int event,  String path) {
                Log.i("anr-test", "AnrFileObserver-onEvent("+ event + ", "+path+")");
            }
        };

        mFileObserver2 = new FileObserver(pathname) {
            @Override
            public void onEvent(int event,  String path) {
                Log.i("anr-test", "AnrFileObserver-2-onEvent("+ event + ", "+path+")");
            }
        };

        this.findViewById(R.id.tv_btn_start_monitor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("anr-test", "startWatching");
                mFileObserver.startWatching();
                mFileObserver2.startWatching();
            }
        });

        this.findViewById(R.id.tv_btn_update_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileUtil.writeFile(filename, "this is a test", true);
                } catch (Throwable e) {
                    e.printStackTrace();
                }

            }
        });

        this.findViewById(R.id.tv_btn_stop_monitor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("anr-test", "stopWatching");
                mFileObserver.stopWatching();
                mFileObserver2.stopWatching();
            }
        });

        this.findViewById(R.id.tv_btn_create_anr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("anr-test", "tv_btn_create_anr");
                while (true) {

                }
            }
        });
    }
}
