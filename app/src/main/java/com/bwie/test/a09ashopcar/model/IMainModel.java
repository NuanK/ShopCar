package com.bwie.test.a09ashopcar.model;

import com.bwie.test.a09ashopcar.bean.GoodsBean;
import com.bwie.test.a09ashopcar.net.OnNetListener;

/**
 * Created by ASUS on 2017/11/16.
 */

public interface IMainModel {
    public void getGoods(OnNetListener<GoodsBean> onNetListener);
}
