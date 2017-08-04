package com.tanlifei.framework.main.ui.activity;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.tanlifei.common.ui.activity.BaseActivity;
import com.tanlifei.framework.R;
import com.tanlifei.framework.main.adapter.GuideAdapter;
import com.tanlifei.framework.main.presenter.IGuidePresenter;
import com.tanlifei.framework.main.presenter.impl.GuidePresenterImpl;
import com.tanlifei.framework.main.presenter.impl.SplashPresenterImpl;
import com.tanlifei.framework.main.ui.view.GuideView;
import com.tlf.basic.uikit.viewpager.CircleIndicator;
import com.tlf.basic.utils.AppCacheUtils;
import com.tlf.basic.utils.StartActUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

/**
 * 第一次启动引导界面
 * Created by tanlifei on 16/1/19.
 */

@Fullscreen //全屏
@EActivity(R.layout.main_activity_guide)
public class GuideActivity extends BaseActivity implements GuideView,
        ViewPager.OnPageChangeListener, View.OnClickListener {

    @ViewById(R.id.guide_pager)
    ViewPager guidePager;
    @ViewById(R.id.indicator)
    CircleIndicator indicator;
    private IGuidePresenter presenter;

    @AfterViews
    void init() {
        presenter = new GuidePresenterImpl(this, mContext);
        guidePager.setAdapter(new GuideAdapter(presenter.addGuideViews(this)));
        indicator.setViewPager(guidePager,presenter.addGuideViews(this).size());
    }

    @Override
    public void onClick(View v) {
        AppCacheUtils.getInstance(mContext).put(SplashPresenterImpl.FIRST_LAUNCHER_APP_TAG, false);//设置为已打开过该应用了
        StartActUtils.start(mContext, HomeActivity_.class);
        StartActUtils.finish(mContext);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < indicator.getChildCount(); i++) {
            indicator.getChildAt(i).setSelected(false);
        }
        indicator.getChildAt(position).setSelected(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
