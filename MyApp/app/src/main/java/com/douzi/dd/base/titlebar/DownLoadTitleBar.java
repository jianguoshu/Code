package com.douzi.dd.base.titlebar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by douzi on 2017/6/16.
 */

public class DownLoadTitleBar extends TitleBar<DownLoadTitleBar> {

    private ImageView btnSetWall;

    public DownLoadTitleBar(Context context, Type type, ViewGroup parent) {
        super(context, type, parent);
    }

    public DownLoadTitleBar setWall(){
        if (btnSetWall == null) {
            btnSetWall = TitleBarResourceHelper.createSetWallImageBtn(mContext, btnContainerRight, mType);
            btnSetWall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSetWallClick();
                }
            });
        }
        return this;
    }

    public void onSetWallClick(){

    }
}
