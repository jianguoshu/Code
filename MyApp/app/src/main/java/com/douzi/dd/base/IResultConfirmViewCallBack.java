package com.douzi.dd.base;

/**
 * Created by douzi on 2017/8/28.
 */

public interface IResultConfirmViewCallBack extends ILoadingViewCallBack, IConfirmViewCallBack{

    String getLeftBtnText();

    String getRightBtnText();

    String getMessage();
}
