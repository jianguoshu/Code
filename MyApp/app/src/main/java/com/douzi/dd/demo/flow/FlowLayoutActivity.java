package com.douzi.dd.demo.flow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;
import com.douzi.dd.base.CollapsibleFlowLayout;

public class FlowLayoutActivity extends BaseActivity {

    private CollapsibleFlowLayout flowLayout;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, FlowLayoutActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        flowLayout = this.findViewById(R.id.collapse_layout);
        this.findViewById(R.id.btn_expend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flowLayout.expand(false);
            }
        });
        this.findViewById(R.id.btn_collapse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flowLayout.collapse(false);
            }
        });
    }
}
