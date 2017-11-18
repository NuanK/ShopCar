package com.bwie.test.a09ashopcar.net;

/**
 * Created by ASUS on 2017/11/16.
 */

public interface OnNetListener<T> {
    public void onSuccess(T t);
    public void onFailure(Exception e);
}
