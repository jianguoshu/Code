package com.douzi.dd.base.titlebar;

import android.content.Context;
import android.view.View;

import com.douzi.dd.MyApplication;

public abstract class BaseHolder<D, P> {

    protected Context mContext;
    protected View mContentView;
    protected D mData;
    protected P[] params;

    @SuppressWarnings("unchecked")
    public BaseHolder() {
        this(null);
    }

    /**
     * 此方法将不定参数保存到了params成员数组中，继承者需要复写initParams()方法并在该方法中去处理传入的参数
     * @param context
     * @param params
     */
    public BaseHolder(Context context, P... params) {
        this.mContext = context;
        if (mContext == null) {
            mContext = MyApplication.getInstance();
        }
        this.params = params;
        initParams();
        mContentView = initView();
        mContentView.setTag(this);
    }

    protected void initParams() {
    };

    /**
     * 更新及填充数据
     *
     * @param data
     */
    public final void setData(D data) {
        this.mData = data;
        if (mData != null) {
            refreshView();
        }
    }

    /**
     * 实例化View
     *
     * @return
     */
    protected abstract View initView();

    /**
     * 填充数据
     */
    protected abstract void refreshView();

    public final View getRootView() {
        return mContentView;
    }

}