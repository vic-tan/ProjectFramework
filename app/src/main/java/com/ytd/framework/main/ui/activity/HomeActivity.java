package com.ytd.framework.main.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tlf.basic.photoview.galleryfinal.permission.AfterPermissionGranted;
import com.tlf.basic.photoview.galleryfinal.permission.EasyPermissions;
import com.ytd.common.ui.activity.actionbar.BaseActionBarActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.presenter.IProperyPresenter;
import com.ytd.framework.equipment.presenter.impl.ProperyPresenterImpl;
import com.ytd.framework.main.adapter.HomeNavigatorFragmentAdapter;
import com.ytd.framework.main.ui.navigator.FragmentNavigator;
import com.ytd.framework.main.ui.view.HomeNavigatorView;
import com.ytd.uikit.actionbar.ActionBarOptViewTagLevel;
import com.ytd.uikit.actionbar.OnOptClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * 首页界面
 * Created by ytd on 16/1/19.
 */
@EActivity(R.layout.main_activity_home)
public class HomeActivity extends BaseActionBarActivity implements HomeNavigatorView.OnBottomNavigatorViewItemClickListener, EasyPermissions.PermissionCallbacks {


    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    public static final String TAG = HomeActivity.class.getSimpleName();
    private static final int DEFAULT_POSITION = 0;
    @ViewById
    HomeNavigatorView bottomNavigatorView;
    private FragmentNavigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mNavigator = new FragmentNavigator(getFragmentManager(), new HomeNavigatorFragmentAdapter(), R.id.container);
        mNavigator.setDefaultPosition(DEFAULT_POSITION);
        mNavigator.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void init() {
//        startService(new Intent(this, CheckAppUpdateService.class));
        initActionBar();
        actionBarView.setActionbarTitle("深圳市源泰达医院");
        actionBarView.setActionbarBackDimiss(true);
        actionBarView.setOnOptClickListener(new OnOptClickListener() {
            @Override
            public void onClick(View v, ActionBarOptViewTagLevel viewTag) {
                start();

            }
        });
        if (bottomNavigatorView != null) {
            bottomNavigatorView.setOnBottomNavigatorViewItemClickListener(this);
        }
        setCurrentTab(mNavigator.getCurrentPosition());
        //TODO
        addTestData();
    }




    //TODO
    IProperyPresenter properyPresenter;
    public void addTestData() {
        properyPresenter = new ProperyPresenterImpl();
        properyPresenter.deleteAll(mContext);
        properyPresenter.save(mContext, PropertyBean.addTestListBean());
    }

    private void start() {
        /*Intent intent = new Intent(mContext,EquipmentScanActivity.class);
                    StartActUtils.forResult(mContext,
                            intent,RESULT_OK);*/
        Intent intent = new Intent(mContext, TestScanActivity.class);
        startActivityForResult(intent, RESULT_OK);
    }


/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         *//* 处理二维码扫描结果
                *//*
        if (requestCode == RESULT_OK) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(mContext, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

    @Override
    public void onBottomNavigatorViewItemClick(int position, View view) {
        setCurrentTab(position);
    }

    public void setCurrentTab(int position) {
        mNavigator.showFragment(position);
        bottomNavigatorView.select(position);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 退出App
     */
    @Override
    public void onBackPressed() {
        exitApp();
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
