package com.ytd.framework.main.ui;


import android.app.Application;
import android.content.Context;

import com.ytd.support.utils.ConfigurationUtils;

import org.litepal.LitePal;

/**
 * 全局Application
 *
 * @author ytd
 * @date 2015年8月13日 上午11:30:51
 */
public class BaseApplication extends Application {

    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        init();
    }

    public void init() {
        ConfigurationUtils.initOKhttp(appContext);//初始化Okhttp
        ConfigurationUtils.initImageLoader(appContext);//初始化图片加载缓存
        ConfigurationUtils.initGalleryFinal(appContext);//初始化上传选择图片器
//        ConfigurationUtils.initCrashHandler(appContext);//设置是否开启全局未捕获异常
        ConfigurationUtils.initCreateFolders(appContext);//创建文件夹
        LitePal.initialize(this);//数据库
    }
}
