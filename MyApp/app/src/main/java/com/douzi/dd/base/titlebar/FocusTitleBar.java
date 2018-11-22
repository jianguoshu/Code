package com.douzi.dd.base.titlebar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by douzi on 2017/6/16.
 */

public class FocusTitleBar extends TitleBar<FocusTitleBar> {

    private ImageView btnAddFocus;
    public ImageView btnFilter;

    public FocusTitleBar(Context context, Type type, ViewGroup parent) {
        super(context, type, parent);
    }

    public FocusTitleBar addFocus() {
        if (btnAddFocus == null) {
            btnAddFocus = TitleBarResourceHelper.createFocusAddImageBtn(mContext, btnContainerRight, mType);
            btnAddFocus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddFocus();
                }
            });
        }
        return this;
    }

    public FocusTitleBar filter() {
        if (btnFilter == null) {
            btnFilter = TitleBarResourceHelper.createFilterImageBtn(mContext, btnContainerRight, mType);
            btnFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFilter();
                }
            });
        }
        return this;
    }

    public void onAddFocus() {

    }

    public void onFilter() {

    }
}
