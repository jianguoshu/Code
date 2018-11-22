package com.douzi.dd.demo.maptest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.douzi.dd.R;
import com.google.common.collect.HashBiMap;

import java.util.HashMap;

public class HashBiMapActivity extends Activity {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, HashBiMapActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_bi_map);
        HashMap<String, String> map1 = new HashMap();
        map1.put("星期1", "Mon");
        map1.put("星期2", "Tue");
        map1.put("星期3", "Wed");
        map1.put("星期4", "Thur");
        map1.put("星期5", "Fri");
        map1.put("星期6", "Sat");
        map1.put("星期7", "Sun");

        HashBiMap<String, String> map = HashBiMap.create(map1);
        for (String str :
                map.keySet()) {
            Log.i(getClass().getSimpleName(), str);
        }

        for (String str :
                map.values()) {
            Log.i(getClass().getSimpleName(), str);
        }
    }
}
