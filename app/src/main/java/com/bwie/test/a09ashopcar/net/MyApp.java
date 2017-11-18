package com.bwie.test.a09ashopcar.net;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by ASUS on 2017/11/18.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration configuration=new
                ImageLoaderConfiguration.Builder(this).build();

        ImageLoader.getInstance().init(configuration);
    }
}
