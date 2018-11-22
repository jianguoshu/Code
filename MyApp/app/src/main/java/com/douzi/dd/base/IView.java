package com.douzi.dd.base;

public interface IView<C extends IViewCallBack> {

    void display(C callBack);

    void displayConfirmView();

    void displayLoadingView();

    void displaySucView();

    void displayFailView();

    void hide();

    boolean isDisplaying();

    boolean isAlive();

    void setAutoCancel(boolean autoCancel);

    boolean isAutoCancel();
}
