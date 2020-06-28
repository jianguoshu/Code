package com.douzi.dd.db;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.douzi.dd.Consts;
import com.douzi.dd.utils.Logger;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    DBHelper(@NonNull Context context, @Nullable String name) {
        super(context, name, null, DB_VERSION);
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @TargetApi(Build.VERSION_CODES.P)
    public DBHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Logger.i(Consts.TAG_SQLITE_TEST, "onCreate("+db+")");
        initDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Logger.i(Consts.TAG_SQLITE_TEST, "onUpgrade("+db+", "+oldVersion+", "+newVersion+")");
        try {
            if (newVersion > oldVersion) {

            } else {
                clearDB(db);
                initDB(db);
            }
        } catch (Exception e) {
            e.printStackTrace();
            clearDB(db);
            initDB(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Logger.i(Consts.TAG_SQLITE_TEST, "onDowngrade("+db+", "+oldVersion+", "+newVersion+")");
        clearDB(db);
        initDB(db);
    }

    private void initDB(SQLiteDatabase db) {
        db.execSQL(UserTable.getInstance().getTableSQL());
        db.execSQL(InfoTable.getInstance().getTableSQL());
    }

    private void clearDB(SQLiteDatabase db) {
        for (String table : listDBTables(db)) {
            db.execSQL(String.format("DROP TABLE IF EXISTS %s ;", table));
        }
    }

    private @NonNull
    ArrayList<String> listDBTables(SQLiteDatabase db) {
        ArrayList<String> tables = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery("select name from sqlite_master where type='table' order by name", null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 遍历出表名
                    String name = cursor.getString(0);

                    /**
                     * 1) 排除android db 自带的两个必须表
                     */
                    if (!name.equals("android_metadata") && !name.equals("sqlite_sequence")) {
                        tables.add(name);
                    }
                }

                cursor.close();
                cursor = null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return tables;
    }
}
