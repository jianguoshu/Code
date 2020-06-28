package com.douzi.dd.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.douzi.dd.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTable<D> implements Table<D> {

    @Override
    public long insert(D d) {
        long id = -1;
        ContentValues values = convert(d);
        if (values != null) {
            id = DBSupplier.getDef().insert(Name.USER, null, values);
        }
        return id;
    }

    @Override
    public long insert(List<D> list) {
        if (ListUtils.isEmpty(list)) {
            return -1;
        }
        int num = 0;
        SQLiteDatabase db = DBSupplier.getDef();
        db.beginTransaction();
        try {
            for (D d : list) {
                if (insert(d) != -1) {
                    num++;
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            num = -1;
        } finally {
            db.endTransaction();
        }
        return num;
    }

    @Override
    public int delete(String whereClause, String[] whereArgs) {
        return DBSupplier.getDef().delete(Name.USER, whereClause, whereArgs);
    }

    @Override
    public int update(D d, String whereClause, String[] whereArgs) {
        int num = 0;
        ContentValues values = convert(d);
        if (values != null) {
            num = DBSupplier.getDef().update(Name.USER, values, whereClause, whereArgs);
        }
        return num;
    }

    @Override
    public int update(List<D> list, String whereClause, String[] whereArgs) {
        if (ListUtils.isEmpty(list)) {
            return 0;
        }
        int num = 0;
        SQLiteDatabase db = DBSupplier.getDef();
        db.beginTransaction();
        try {
            for (D d : list) {
                num += update(d, whereClause, whereArgs);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            num = 0;
        } finally {
            db.endTransaction();
        }
        return num;
    }

    @Override
    public List<D> query(String sql, String[] selectionArgs) {
        List<D> list = null;
        Cursor cursor = DBSupplier.getDef().rawQuery(sql, selectionArgs);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    D d = parse(cursor);
                    if (d != null) {
                        list.add(d);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public void clear() {
        try {
            String sql = "delete from " + getTableName();
            DBSupplier.getDef().execSQL(sql);
            String initialSql = "update sqlite_sequence set seq=0 where name='" + getTableName() + "'";
            DBSupplier.getDef().execSQL(initialSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
