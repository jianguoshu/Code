package com.douzi.dd.base.titlebar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by douzi on 2017/6/16.
 */

public class NovelTitleBar extends TitleBar<NovelTitleBar> {

    ImageView btnNovelAccount;


    public NovelTitleBar(Context context, Type type, ViewGroup parent) {
        super(context, type, parent);
    }

    public NovelTitleBar novelAccount() {
        if (btnNovelAccount == null) {
            btnNovelAccount = TitleBarResourceHelper.createNovelAccountImageBtn(mContext, btnContainerRight, mType);
            btnNovelAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNovelAccountClick();
                }
            });
        }
        setNovelAccountAble(true);
        return this;
    }

    public void setNovelAccountAble(boolean backAble) {
        if (btnNovelAccount != null) {
            btnNovelAccount.setVisibility(backAble ? View.VISIBLE : View.GONE);
        }
    }

    public void onNovelAccountClick(){

    }
}
