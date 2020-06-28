package com.douzi.dd.demo.sqlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.Consts;
import com.douzi.dd.R;
import com.douzi.dd.db.InfoTable;
import com.douzi.dd.db.Table;
import com.douzi.dd.db.UserTable;
import com.douzi.dd.utils.Logger;

import java.util.List;

public class SqliteTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_test);

        findViewById(R.id.btn_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserTable.getInstance().insert(createUser("1", "one"));
                UserTable.getInstance().insert(createUser("2", "two"));
                UserTable.getInstance().insert(createUser("3", "three"));
            }
        });
        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserTable.getInstance().clear();
            }
        });
        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserTable.getInstance().insert(createUser("1", "one"));
                UserTable.getInstance().insert(createUser("2", "two"));
                UserTable.getInstance().insert(createUser("3", "three"));
            }
        });

        findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.i(Consts.TAG_SQLITE_TEST, "query-start----------");
                pintList(UserTable.getInstance().query("select * from " + Table.Name.USER, null));
                Logger.i(Consts.TAG_SQLITE_TEST, "----------query-end");
            }
        });


        findViewById(R.id.btn_insert_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoTable.getInstance().insert(createInfo("1", 20));
                InfoTable.getInstance().insert(createInfo("2", 21));
                InfoTable.getInstance().insert(createInfo("3", 22));
            }
        });
        findViewById(R.id.btn_clear_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoTable.getInstance().clear();
            }
        });
        findViewById(R.id.btn_update_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoTable.getInstance().insert(createInfo("1", 20));
                InfoTable.getInstance().insert(createInfo("2", 21));
                InfoTable.getInstance().insert(createInfo("3", 22));
            }
        });

        findViewById(R.id.btn_query_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.i(Consts.TAG_SQLITE_TEST, "query-start----------");
                pintList(InfoTable.getInstance().query("select * from " + Table.Name.USER, null));
                Logger.i(Consts.TAG_SQLITE_TEST, "----------query-end");
            }
        });
    }

    public static void startAct(Context context) {
        Intent intent = new Intent(context, SqliteTestActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    private void pintList(List<? extends Object> list) {
        if (list == null) {
            return;
        }
        for (Object object : list) {
            Logger.i(Consts.TAG_SQLITE_TEST, object.toString());
        }
    }

    private User createUser(String id, String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }

    private Info createInfo(String id, int age) {
        Info info = new Info();
        info.setUserId(id);
        info.setAge(age);
        return info;
    }
}
