package com.tanlifei.framework.main.presenter;

/**
 * Created by tanlifei on 16/5/12.
 */
public interface ISplashPresenter {

    void delayedStart(long delayed,boolean isLoadingData);//多少秒间隔启动,是否有加载资源过度界面
}
