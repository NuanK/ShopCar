package com.bwie.test.a09ashopcar.view;

import com.bwie.test.a09ashopcar.bean.GoodsBean;

import java.util.List;

/**
 * Created by ASUS on 2017/11/16.
 */

public interface IShopActivity {
    public void showList(List<GoodsBean.DataBean>groupList,List<List<GoodsBean.DataBean.DatasBean>>childList);
}
