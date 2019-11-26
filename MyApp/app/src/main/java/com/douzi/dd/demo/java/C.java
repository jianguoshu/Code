package com.douzi.dd.demo.java;

import android.util.Log;

public class C implements A, B{
    @Override
    public void say() {
        Log.i("java-type", "A say");
    }

    @Override
    public void talk() {
        Log.i("java-type", "B talk");
    }
}
