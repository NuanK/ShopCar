package com.bwie.test.a09ashopcar.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.test.a09ashopcar.R;
import com.bwie.test.a09ashopcar.adapter.ShopAdapter;
import com.bwie.test.a09ashopcar.bean.GoodsBean;
import com.bwie.test.a09ashopcar.eventbusevent.MessageEvent;
import com.bwie.test.a09ashopcar.eventbusevent.PriceAndCountEvent;
import com.bwie.test.a09ashopcar.presenter.MainPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class ShopActivity extends AppCompatActivity implements IShopActivity {

    private ExpandableListView mElv;
    private CheckBox mQuanCk;
    /**
     * 0
     */
    private TextView mTvPrice;
    /**
     * 结算(0)
     */
    private TextView mTvNum;
    private LinearLayout mActivityMain;
    private ShopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        EventBus.getDefault().register(this);
        initView();
        new MainPresenter(this).getGoods();
        mQuanCk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.changeAllListCbState(mQuanCk.isChecked());
            }
        });

    }

    private void initView() {
        mElv = (ExpandableListView) findViewById(R.id.elv);
        mQuanCk = (CheckBox) findViewById(R.id.quan_ck);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvNum = (TextView) findViewById(R.id.tv_num);
        mActivityMain = (LinearLayout) findViewById(R.id.activity_main);
    }


    @Override
    public void showList(List<GoodsBean.DataBean> groupList, List<List<GoodsBean.DataBean.DatasBean>> childList) {
        adapter=new ShopAdapter(this,groupList,childList);
        mElv.setAdapter(adapter);
        //去掉二级列表的小箭头
        mElv.setGroupIndicator(null);
        //默认让其全部展开
        for (int i=0;i<groupList.size();i++){
            mElv.expandGroup(i);
        }
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        mQuanCk.setChecked(event.isChecked());
    }

    @Subscribe
    public void onMessageEvent(PriceAndCountEvent event) {
        mTvNum.setText("结算(" + event.getCount() + ")");
        mTvPrice.setText(event.getPrice() + "");
    }


}
