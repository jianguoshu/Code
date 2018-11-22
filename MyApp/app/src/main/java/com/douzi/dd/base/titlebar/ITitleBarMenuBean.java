package com.douzi.dd.base.titlebar;

import android.os.Parcelable;

public interface ITitleBarMenuBean extends Parcelable {
    String getName();

    int getImageIdDefault();

    String getImageUrl();

    int getImageId();

    TitleBar.MenuType getType();
}
