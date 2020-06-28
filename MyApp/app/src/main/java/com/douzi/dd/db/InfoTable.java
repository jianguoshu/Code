package com.douzi.dd.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.douzi.dd.demo.sqlite.Info;

public class InfoTable extends BaseTable<Info> {

    @interface COLUMN {
        String USER_ID = "user_id";
        String AGE = "age";
    }

    private static final InfoTable instance = new InfoTable();

    public static InfoTable getInstance() {
        return instance;
    }

    @NonNull
    @Override
    public String getTableSQL() {
        return "CREATE TABLE IF NOT EXISTS " + Table.Name.INFO +
                " (" + InfoTable.COLUMN.USER_ID + " TEXT , " +
                InfoTable.COLUMN.AGE + " INTEGER DEFAULT 0 , " +
                "FOREIGN KEY(" + InfoTable.COLUMN.USER_ID + ") REFERENCES " + Table.Name.USER + "(" + UserTable.COLUMN.USER_ID + ") on delete cascade on update cascade on insert cascade" +
                ")";
    }

    @Override
    public String getTableName() {
        return Name.INFO;
    }

    @Override
    public ContentValues convert(Info info) {
        ContentValues values = new ContentValues();
        values.put(InfoTable.COLUMN.USER_ID, info.getUserId());
        values.put(InfoTable.COLUMN.AGE, info.getAge());
        return values;
    }

    @Override
    public Info parse(@NonNull Cursor cursor) {
        Info info = new Info();
        info.setUserId(cursor.getString(cursor.getColumnIndex(COLUMN.USER_ID)));
        info.setAge(cursor.getInt(cursor.getColumnIndex(COLUMN.AGE)));
        return info;
    }
}
