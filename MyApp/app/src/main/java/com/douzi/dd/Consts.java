package com.douzi.dd;

import com.douzi.dd.demo.servicetest.ServiceLifecycleActivity;
import com.douzi.dd.demo.sqlite.SqliteTestActivity;

/**
 * Created by douzi on 2017/8/3.
 */

public class Consts {
    private static final String TAG = "tag_";
    public static final String TAG_SERVICE_TEST = TAG + ServiceLifecycleActivity.class.getSimpleName();
    public static final String TAG_SQLITE_TEST = TAG + SqliteTestActivity.class.getSimpleName();
}
