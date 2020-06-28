package com.douzi.dd.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.douzi.dd.demo.sqlite.User;

public class UserTable extends BaseTable<User> {

    @interface COLUMN {
        String USER_ID = "user_id";
        String NAME = "name";
    }

    private static final UserTable instance = new UserTable();

    public static UserTable getInstance() {
        return instance;
    }

    @NonNull
    @Override
    public String getTableSQL() {
        return "CREATE TABLE IF NOT EXISTS " + Table.Name.USER +
                " (" + UserTable.COLUMN.USER_ID + " TEXT PRIMARY KEY , " +
                UserTable.COLUMN.NAME + " TEXT" +
                ")";
    }

    @Override
    public String getTableName() {
        return Name.USER;
    }

    @Override
    public ContentValues convert(User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN.USER_ID, user.getId());
        values.put(COLUMN.NAME, user.getName());
        return values;
    }

    @Override
    public @NonNull User parse(@NonNull Cursor cursor) {
        User user = new User();
        user.setId(cursor.getString(cursor.getColumnIndex(COLUMN.USER_ID)));
        user.setName(cursor.getString(cursor.getColumnIndex(COLUMN.NAME)));
        return user;
    }
}
