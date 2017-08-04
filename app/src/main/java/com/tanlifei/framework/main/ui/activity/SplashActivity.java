package com.tanlifei.framework.main.ui.activity;

import com.tanlifei.common.ui.activity.BaseActivity;
import com.tanlifei.framework.R;
import com.tanlifei.framework.main.presenter.ISplashPresenter;
import com.tanlifei.framework.main.presenter.impl.SplashPresenterImpl;
import com.tanlifei.framework.main.ui.view.SplashView;
import com.tlf.basic.utils.StartActUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

/**
 * 启动界面
 * Created by tanlifei on 16/1/19.
 */
@Fullscreen //全屏
@EActivity(R.layout.main_activity_splash)
public class SplashActivity extends BaseActivity implements SplashView {

    public static final long DELAYED = 3000;
    public static final boolean IS_HAS_LOADING_DATA = false;//是否有加载数据过度界面
    private ISplashPresenter presenter;




    @AfterViews
    void init() {
        presenter = new SplashPresenterImpl(this, this);
        presenter.delayedStart(DELAYED, IS_HAS_LOADING_DATA);
    }


    private void startAct(Class clazz) {
        StartActUtils.start(mContext, clazz);
        StartActUtils.finish(mContext);
    }

    @Override
    public void gotoGuideAct() {
        startAct(GuideActivity_.class);//进入引导页
    }

    @Override
    public void gotoLoadingAct() {
        startAct(LoadingActivity.class);//进入加载资源数据页
    }

    @Override
    public void gotoHomeAct() {
        startAct(HomeActivity_.class);//进入首页
    }

    /**
     * 退出App
     */
    @Override
    public void onBackPressed() {
        exitApp();
    }

    @Override
    protected void setSystemBarTint(int systemBarTint) {
    }
}
