package com.tanlifei.framework.main.presenter.impl;

import android.content.Context;

import com.tanlifei.framework.main.presenter.ISplashPresenter;
import com.tanlifei.framework.main.ui.view.SplashView;
import com.tlf.basic.utils.AppCacheUtils;
import com.tlf.basic.utils.CountDownTimer;


/**
 * Created by tanlifei on 16/5/12.
 */
public class SplashPresenterImpl implements ISplashPresenter {

    public static final String FIRST_LAUNCHER_APP_TAG = "first_splash_app_tag";//保存第一次启动app的key
    private SplashView launcher;
    private Context mContext;

    public SplashPresenterImpl(SplashView launcherView, Context mContext) {
        this.launcher = launcherView;
        this.mContext = mContext;
    }

    @Override
    public void delayedStart(long delayed, final boolean isLoadingData) {
        new CountDownTimer(delayed, delayed) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (AppCacheUtils.getInstance(mContext).getBoolean(FIRST_LAUNCHER_APP_TAG, true)) {//第一次打开应用,进入引导页
                    launcher.gotoGuideAct();//进入引导页
                } else {//进入正在加载数据页
                    if (isLoadingData) {//有加载数据页时过程
                        launcher.gotoLoadingAct();//进入加载资源数据页
                    } else {//没有正在加载数据页时,直接进入首页
                        launcher.gotoHomeAct();//进入首页
                    }
                }
            }
        }.start();
    }
}
