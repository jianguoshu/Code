package com.douzi.dd.demo.anr;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.douzi.dd.R;
import com.douzi.dd.utils.FileUtil;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ANRActivity extends Activity {

    private FileObserver mFileObserver2;
    private FileObserver mFileObserver;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

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

        mFileObserver = new FileObserver("/data/anr/") {
            @Override
            public void onEvent(int event, String path) {
                Log.i("anr-test", "AnrFileObserver-onEvent(" + event + ", " + path + ")");
            }
        };

        mFileObserver2 = new FileObserver("/data/anr/traces.txt") {
            @Override
            public void onEvent(int event, String path) {
                Log.i("anr-test", "AnrFileObserver-2-onEvent(" + event + ", " + path + ")");
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
                try {
                    blockThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        this.findViewById(R.id.tb_btn_anr_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readAnr();
            }
        });
    }

    private void readAnr() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
        String s = FileUtil.readStringFromFile("/data/anr/traces.txt");
        Log.i("anr-test", s);
    }

    private void blockThread() throws InterruptedException, ExecutionException {
        FutureTask task = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                int i = 0;
                while (i < 1000) {
                    i = 0;
                }
                return null;
            }
        });

        new Thread(task).start();
        task.get();
    }
}
