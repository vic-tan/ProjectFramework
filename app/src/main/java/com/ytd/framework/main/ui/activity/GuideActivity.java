package com.ytd.framework.main.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tlf.basic.photoview.galleryfinal.permission.AfterPermissionGranted;
import com.tlf.basic.photoview.galleryfinal.permission.EasyPermissions;
import com.tlf.basic.uikit.viewpager.CircleIndicator;
import com.tlf.basic.utils.StartActUtils;
import com.ytd.common.ui.activity.BaseActivity;
import com.ytd.framework.R;
import com.ytd.framework.main.adapter.GuideAdapter;
import com.ytd.framework.main.bean.ConfigBean;
import com.ytd.framework.main.presenter.IGuidePresenter;
import com.ytd.framework.main.presenter.impl.ConfigPresenterImpl;
import com.ytd.framework.main.presenter.impl.GuidePresenterImpl;
import com.ytd.framework.main.ui.view.GuideView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * 第一次启动引导界面
 * Created by ytd on 16/1/19.
 */

@Fullscreen //全屏
@EActivity(R.layout.main_activity_guide)
public class GuideActivity extends BaseActivity implements GuideView,
        ViewPager.OnPageChangeListener, View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    @ViewById(R.id.guide_pager)
    ViewPager guidePager;
    @ViewById(R.id.indicator)
    CircleIndicator indicator;
    private IGuidePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions();
    }

    @AfterViews
    void init() {
        presenter = new GuidePresenterImpl(this, mContext);
        guidePager.setAdapter(new GuideAdapter(presenter.addGuideViews(this)));
        indicator.setViewPager(guidePager, presenter.addGuideViews(this).size());
    }


    @Override
    public void onClick(View v) {
        //TODO
//        StartActUtils.start(mContext, ConfigActivity_.class);

        StartActUtils.start(mContext, LoginActivity_.class);
        StartActUtils.finish(mContext);
    }

    private void text() {
        ConfigBean bean = new ConfigBean();
        bean.setUrl("http://1811o171v6.iask.in");
        new ConfigPresenterImpl().save(bean);
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

    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(List<String> perms) {

    }
}
