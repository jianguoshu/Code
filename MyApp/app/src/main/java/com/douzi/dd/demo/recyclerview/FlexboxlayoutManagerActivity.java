package com.douzi.dd.demo.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;
import com.douzi.dd.utils.DeviceUtil;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class FlexboxlayoutManagerActivity extends BaseActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<String> mText;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, FlexboxlayoutManagerActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexboxlayout_manager);

        initData();

        initViews();
    }

    private void initData() {
        mText = new ArrayList<>();
        mText.add("程序员");
        mText.add("影视天堂");
        mText.add("美食");
        mText.add("漫画.手绘");
        mText.add("广告圈");
        mText.add("旅行.在路上");
        mText.add("娱乐八卦");
        mText.add("青春");
        mText.add("谈写作");
        mText.add("短篇小说");
        mText.add("散文");
        mText.add("摄影");
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycler_view);

        final FlexboxLayoutManager manager = new FlexboxLayoutManager();
        //设置主轴排列方式
        manager.setFlexDirection(FlexDirection.ROW);
        //设置是否换行
        manager.setFlexWrap(FlexWrap.WRAP);
        manager.setAlignItems(AlignItems.FLEX_START);
        mRecyclerView.setLayoutManager(manager);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator() {
            @Override
            public void onAnimationFinished(@NonNull RecyclerView.ViewHolder viewHolder) {
//                mAdapter.notifyDataSetChanged();
            }
        };

        mRecyclerView.setItemAnimator(itemAnimator);
        mAdapter = new RecyclerView.Adapter<ChannelHolder>() {

            @Override
            public ChannelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(FlexboxlayoutManagerActivity.this).inflate(R.layout.item_channel, parent, false);
                ChannelHolder holder = new ChannelHolder(view);
                holder.name = (TextView) view.findViewById(R.id.tv_name);
                holder.btnExpand = (TextView) view.findViewById(R.id.btn_expand);
                Log.i("douzi", "onCreateViewHolder(" + holder + ")");
                return holder;
            }

            @Override
            public void onBindViewHolder(final ChannelHolder holder, final int position) {
                Log.i("douzi", "onBindViewHolder(" + holder + ", " + position + "" + ")");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mText.remove(holder.getPosition());
                        Log.i("douzi", "remove(" + holder.getPosition() + ")");
                        mAdapter.notifyItemRemoved(holder.getPosition());
                    }
                });
                holder.name.setText(mText.get(holder.getPosition()));
                holder.btnExpand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.btnExpand.setVisibility(View.GONE);
                    }
                });

                ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                if (lp instanceof FlexboxLayoutManager.LayoutParams) {
                    FlexboxLayoutManager.LayoutParams flexboxLp =
                            (FlexboxLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                    flexboxLp.setFlexGrow(0.0f);
                }
            }

            @Override
            public int getItemCount() {
                Log.i("douzi", "getItemCount(" + mText.size() + ")");
                return mText.size();
            }

            @Override
            public int getItemViewType(int position) {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        final ViewGroup.LayoutParams layoutParams = mRecyclerView.getLayoutParams();
        this.findViewById(R.id.btn_expand).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                mRecyclerView.setLayoutParams(layoutParams);
            }
        });
        this.findViewById(R.id.btn_collapse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutParams.height = DeviceUtil.dip2px(80);
                mRecyclerView.setLayoutParams(layoutParams);
            }
        });
    }

    public static class ChannelHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView btnExpand;

        public ChannelHolder(View itemView) {
            super(itemView);
        }
    }
}

