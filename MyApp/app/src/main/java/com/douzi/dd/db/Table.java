package com.douzi.dd.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.List;

public interface Table<D> {

    @interface Name {
        String USER = "user";
        String INFO = "info";
    }

    @NonNull
    String getTableSQL();

    @Name
    String getTableName();

    ContentValues convert(D user);

    @NonNull D parse(@NonNull Cursor cursor);

    long insert(D d);

    long insert(List<D> list);

    int delete(String whereClause, String[] whereArgs);

    int update(D d, String whereClause, String[] whereArgs);

    int update(List<D> list, String whereClause, String[] whereArgs);

    List<D> query(String sql, String[] selectionArgs);

    void clear();
}
