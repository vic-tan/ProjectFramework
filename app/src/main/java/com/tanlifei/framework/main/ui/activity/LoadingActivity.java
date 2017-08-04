package com.tanlifei.framework.main.ui.activity;

import android.os.Bundle;

import com.tanlifei.common.ui.activity.BaseActivity;

/**
 * 加载资源数据界面
 * Created by tanlifei on 16/1/19.
 */
public class LoadingActivity extends BaseActivity {
    public static final String TAG = LoadingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
