package com.bwie.test.a09ashopcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.a09ashopcar.R;
import com.bwie.test.a09ashopcar.bean.GoodsBean;
import com.bwie.test.a09ashopcar.eventbusevent.MessageEvent;
import com.bwie.test.a09ashopcar.eventbusevent.PriceAndCountEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by ASUS on 2017/11/16.
 */

public class ShopAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<GoodsBean.DataBean>groupList;
    private List<List<GoodsBean.DataBean.DatasBean>>childList;
    private final LayoutInflater inflater;

    public ShopAdapter(Context context, List<GoodsBean.DataBean> groupList, List<List<GoodsBean.DataBean.DatasBean>> childList) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childList.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childList.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int i, boolean b, View convertView, ViewGroup viewGroup) {
        View view;
        final GroupViewHolder gholder;
        if (convertView==null){
            gholder=new GroupViewHolder();
            view=inflater.inflate(R.layout.item_group,null);
            gholder.cb_group=view.findViewById(R.id.cb_group);
            gholder.tv_number=view.findViewById(R.id.tv_number);
            view.setTag(gholder);
        }else {
            view=convertView;
            gholder= (GroupViewHolder) view.getTag();
        }

        final GoodsBean.DataBean groupBean=groupList.get(i);
        gholder.cb_group.setChecked(groupBean.isChecked());
        gholder.tv_number.setText(groupBean.getTitle());

        //一级checkbox
        gholder.cb_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupBean.setChecked(gholder.cb_group.isChecked());
                changeChildCbState(i, gholder.cb_group.isChecked());
                EventBus.getDefault().post(compute());
                changeAllCbState(isAllGroupCbSelected());
                notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, final View convertView, ViewGroup viewGroup) {

        View view;
        final ChildViewHolder cholder;
        if (convertView==null){
            cholder=new ChildViewHolder();
            view=inflater.inflate(R.layout.item_child,null);
            cholder.cb_child=view.findViewById(R.id.cb_child);
            cholder.tv_tel=view.findViewById(R.id.tv_tel);
            cholder.tv_content=view.findViewById(R.id.tv_content);
            cholder.tv_time=view.findViewById(R.id.tv_time);
            cholder.tv_pri=view.findViewById(R.id.tv_pri);
            cholder.tv_del=view.findViewById(R.id.tv_del);
            cholder.iv_add = view.findViewById(R.id.iv_add);
            cholder.iv_del = view.findViewById(R.id.iv_del);
            cholder.tv_num = view.findViewById(R.id.tv_num);
            view.setTag(cholder);
        }else {
            view=convertView;
            cholder= (ChildViewHolder) view.getTag();
        }

        final GoodsBean.DataBean.DatasBean childBean=childList.get(i).get(i1);
        cholder.cb_child.setChecked(childBean.isChecked());
        cholder.tv_tel.setText(childBean.getType_name());
        cholder.tv_content.setText(childBean.getMsg());
        cholder.tv_time.setText(childBean.getAdd_time());
        cholder.tv_pri.setText(childBean.getPrice()+"");
        cholder.tv_num.setText(childBean.getNum() + "");

        //二级checkbox
        //给holder.cbChild设置点击事件
        cholder.cb_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置该条目对象里的checked属性值
                childBean.setChecked(cholder.cb_child.isChecked());
                PriceAndCountEvent priceAndCountEvent=compute();
                EventBus.getDefault().post(priceAndCountEvent);
                if (cholder.cb_child.isChecked()){
                    //当前checkbox是选中状态
                    if (isAllChildCbSelected(i)){
                        changGroupCbState(i,true);
                        changeAllCbState(isAllGroupCbSelected());

                    }
                }else {
                    changGroupCbState(i,false);
                    changeAllCbState(isAllGroupCbSelected());
                }

                notifyDataSetChanged();

            }
        });
        //加号
        cholder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=childBean.getNum();
                cholder.tv_num.setText(++num+"");
                childBean.setNum(num);
                if (cholder.cb_child.isChecked()){
                    PriceAndCountEvent priceAndCountEvent=compute();
                    EventBus.getDefault().post(priceAndCountEvent);
                }
            }
        });

        //减号
        cholder.iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=childBean.getNum();
                if (num==1){
                    Toast.makeText(context,"最少留一个呗！",Toast.LENGTH_SHORT).show();
                    return;
                }
                cholder.tv_num.setText(--num+"");
                childBean.setNum(num);
                if (cholder.cb_child.isChecked()){
                    PriceAndCountEvent priceAndCountEvent=compute();
                    EventBus.getDefault().post(priceAndCountEvent);
                }
            }
        });
        //删除
        cholder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<GoodsBean.DataBean.DatasBean> datasBeen = childList.get(i);
                GoodsBean.DataBean.DatasBean remove = datasBeen.remove(i1);
                if (datasBeen.size()==0){
                    childList.remove(i);
                    groupList.remove(i);
                }
                EventBus.getDefault().post(compute());
                notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    class GroupViewHolder{
        CheckBox cb_group;
        TextView tv_number;
    }

    class ChildViewHolder{
        CheckBox cb_child;
        TextView tv_tel;
        TextView tv_content;
        TextView tv_time;
        TextView tv_pri;
        TextView tv_del;
        ImageView iv_del;
        ImageView iv_add;
        TextView tv_num;
    }

    /**
     * 改变全选的状态
     *
     * @param flag
     */

        private void changeAllCbState(boolean flag){
            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setChecked(flag);
            EventBus.getDefault().post(messageEvent);
        }

    /**
     * 改变一级列表checkbox状态
     *
     * @param groupPosition
     */
    private void changGroupCbState(int groupPosition,boolean flag){
        GoodsBean.DataBean dataBean = groupList.get(groupPosition);
        dataBean.setChecked(flag);
    }

    /**
     * 改变二级列表checkbox状态
     *
     * @param groupPosition
     * @param flag
     */
    private void changeChildCbState(int groupPosition, boolean flag) {
        List<GoodsBean.DataBean.DatasBean> datasBeen = childList.get(groupPosition);
        for (int i = 0; i <datasBeen.size() ; i++) {
            GoodsBean.DataBean.DatasBean datasBean = datasBeen.get(i);
            datasBean.setChecked(flag);
        }

    }

    /**
     * 判断一级列表是否全部选中
     *
     * @return
     */

    private boolean isAllGroupCbSelected() {
        for (int i = 0; i <groupList.size() ; i++) {
            GoodsBean.DataBean dataBean = groupList.get(i);
            if (!dataBean.isChecked()){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断二级列表是否全部选中
     *
     * @param groupPosition
     * @return
     */

    private boolean isAllChildCbSelected(int groupPosition){
        List<GoodsBean.DataBean.DatasBean> datasBeen = childList.get(groupPosition);
        for (int i=0;i<datasBeen.size();i++){
            GoodsBean.DataBean.DatasBean datasBean = datasBeen.get(i);
            if (!datasBean.isChecked()){
                return false;
            }
        }
        return true;
    }

    /**
     * 计算列表中，选中的钱和数量
     */

    private PriceAndCountEvent compute(){
        int count = 0;
        int price = 0;
        for (int i = 0; i <childList.size() ; i++) {
            List<GoodsBean.DataBean.DatasBean> datasBeen = childList.get(i);
            for (int j = 0; j <datasBeen.size() ; j++) {
                GoodsBean.DataBean.DatasBean datasBean = datasBeen.get(j);
                if (datasBean.isChecked()){
                    price+=datasBean.getNum()*datasBean.getPrice();
                    count+=datasBean.getNum();
                }
            }
        }

        PriceAndCountEvent priceAndCountEvent = new PriceAndCountEvent();
        priceAndCountEvent.setCount(count);
        priceAndCountEvent.setPrice(price);
        return priceAndCountEvent;
    }

    /**
     * 设置全选、反选
     *
     * @param flag
     */
    public void changeAllListCbState(boolean flag) {
        for (int i = 0; i <groupList.size() ; i++) {
            changGroupCbState(i,flag);
            changeChildCbState(i,flag);
        }
        EventBus.getDefault().post(compute());
        notifyDataSetChanged();
    }

}
