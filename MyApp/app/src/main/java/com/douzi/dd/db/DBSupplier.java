package com.douzi.dd.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.douzi.dd.MyApplication;

import java.util.HashMap;

public class DBSupplier {

    private static HashMap<String, DBHelper> dbCache = new HashMap<>();

    public static SQLiteDatabase getDef() {
        return get(MyApplication.getInstance().getApplicationContext(), DB.Name.DEF);
    }

    public static synchronized SQLiteDatabase get(@NonNull Context context, @DB.Name String name) {
        DBHelper helper = dbCache.get(name);
        if (helper == null) {
            helper = new DBHelper(context, name);
            dbCache.put(name, helper);
        }
        return helper.getWritableDatabase();
    }
}
