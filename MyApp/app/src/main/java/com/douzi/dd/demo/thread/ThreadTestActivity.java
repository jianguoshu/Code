package com.douzi.dd.demo.thread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class ThreadTestActivity extends BaseActivity {

    private List<String> mLogList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter<LogHolder> mAdapter;
    private FutureTask futureTask;
    private Future<?> future;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, ThreadTestActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_test);
        initView();
    }

    private void initView() {
        initLogView();

        initTestOneView();
    }

    private void initLogView() {
        mRecyclerView = this.findViewById(R.id.recycler_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RecyclerView.Adapter<LogHolder>() {

            @Override
            public LogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(ThreadTestActivity.this).inflate(R.layout.item_log, parent, false);
                LogHolder holder = new LogHolder(view);
                holder.name = (TextView) view.findViewById(R.id.tv_name);
                return holder;
            }

            @Override
            public void onBindViewHolder(LogHolder holder, final int position) {
                holder.name.setText(mLogList.get(position));
            }

            @Override
            public int getItemCount() {
                return mLogList.size();
            }

            @Override
            public int getItemViewType(int position) {
                return 1;
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    private void logAdd(String... log) {
        mLogList.addAll(Arrays.asList(log));
        mAdapter.notifyItemRangeInserted(mLogList.size() - log.length, log.length);
    }

    private void logClear() {
        mLogList.clear();
        mAdapter.notifyDataSetChanged();
    }

    public static class LogHolder extends RecyclerView.ViewHolder {

        public TextView name;

        public LogHolder(View itemView) {
            super(itemView);
        }
    }

    private void initTestOneView() {
        this.findViewById(R.id.btn_future_task_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFutureTask();
            }
        });

        this.findViewById(R.id.btn_submit_runnable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRunnable();
            }
        });
        this.findViewById(R.id.btn_submit_callable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCallable();
            }
        });
    }

    /**
     * 实际测试使用这种方式 cancel后子线程继续执行
     */
    private void submitRunnable() {
        logClear();
        logAdd("submit-Runnable-start");
        future = Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(500);
                        final int finalI = i;
                        mRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                logAdd("submit-Runnable.run(" + finalI + ")");
                            }
                        });
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    logAdd("submit-Runnable.cancel()");
                    future.cancel(true);
                    logAdd("submit-Runnable.get()");
                    future.get();
                } catch (Throwable e) {
                    e.printStackTrace();
                    logAdd(e.toString());
                }
                logAdd("submit-Runnable.run()-end");
            }
        }, 3000);
    }

    /**
     * 实际测试使用这种方式 cancel后子线程继续执行
     */
    private void submitCallable() {
        logClear();
        logAdd("submit-Callable-start");
        future = Executors.newSingleThreadExecutor().submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(500);
                        final int finalI = i;
                        mRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                logAdd("submit-Callable.call(" + finalI + ")");
                            }
                        });
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        });

        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    logAdd("submit-Callable.cancel()");
                    future.cancel(true);
                    logAdd("submit-Callable.get()");
                    future.get();
                } catch (Throwable e) {
                    e.printStackTrace();
                    logAdd(e.toString());
                }
                logAdd("submit-Callable.call()-end");
            }
        }, 3000);
    }

    /**
     * 实际测试使用这种方式 cancel后子线程能够停止
     */
    private void startFutureTask() {
        logClear();
        futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(500);
                    final int finalI = i;
                    Log.i("douzi", "submit-Runnable.run(" + finalI + ")");
                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            logAdd("futureTask.call(" + finalI + ")");
                        }
                    });
                    Thread.sleep(500);
                }
                return null;
            }
        });
        logAdd("futureTask.run()-start");
        new Thread(futureTask).start();
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    logAdd("futureTask.cancel()");
                    futureTask.cancel(true);
                    logAdd("futureTask.get()");
                    futureTask.get();
                } catch (Throwable e) {
                    e.printStackTrace();
                    logAdd(e.toString());
                }
                logAdd("futureTask.run()-end");
            }
        }, 3000);
    }
}
