package com.bwie.test.a09ashopcar.presenter;

import com.bwie.test.a09ashopcar.bean.GoodsBean;
import com.bwie.test.a09ashopcar.model.IMainModel;
import com.bwie.test.a09ashopcar.model.MainModel;
import com.bwie.test.a09ashopcar.net.OnNetListener;
import com.bwie.test.a09ashopcar.view.IShopActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/11/16.
 */

public class MainPresenter {
    private IMainModel iMainModel;
    private IShopActivity iMainActivity;
    public MainPresenter(IShopActivity iMainActivity){
        this.iMainActivity=iMainActivity;
        iMainModel=new MainModel();
        }

    public void getGoods(){
        iMainModel.getGoods(new OnNetListener<GoodsBean>() {
            @Override
            public void onSuccess(GoodsBean goodsBean) {
                List<GoodsBean.DataBean> groupList = goodsBean.getData();
                List<List<GoodsBean.DataBean.DatasBean>>childList=new ArrayList<List<GoodsBean.DataBean.DatasBean>>();
                for (int i=0;i<groupList.size();i++){
                    List<GoodsBean.DataBean.DatasBean>datas=groupList.get(i).getDatas();
                    childList.add(datas);
                }
                iMainActivity.showList(groupList,childList);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}

