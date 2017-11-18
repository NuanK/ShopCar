package com.bwie.test.a09ashopcar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bwie.test.a09ashopcar.R;
import com.bwie.test.a09ashopcar.view.ShopActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/11/18.
 */

public class RecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> list;

    public RecyAdapter( Context context, List<String> list) {

        this.context = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_recy,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String str = list.get(position);
        MyViewHolder myviewHolder = (MyViewHolder) holder;
        ImageLoader.getInstance().displayImage(str,myviewHolder.re_img);

        myviewHolder.re_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ShopActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView re_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            re_img=(ImageView)itemView.findViewById(R.id.re_img);
            int width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
            ViewGroup.LayoutParams params = re_img.getLayoutParams();
            //设置图片的相对于屏幕的宽高比
            params.width = width/3;
            params.height =  (int) (200 + Math.random() * 400) ;
            re_img.setLayoutParams(params);

        }
    }
}
