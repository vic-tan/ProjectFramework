package com.ytd.common.ui.activity;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Window;
import android.view.WindowManager;

import com.tlf.basic.base.autolayout.AutoLayoutActivity;
import com.tlf.basic.base.systembartint.SystemBarTintManager;
import com.tlf.basic.utils.ActivityManager;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.framework.R;
import com.ytd.framework.main.ui.service.AppDownloadService;
import com.ytd.framework.main.ui.service.CheckAppUpdateService;
import com.ytd.support.utils.ResUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ytd on 15/12/17.
 */
public abstract class BaseActivity extends AutoLayoutActivity {

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        baseRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityManager.getActivityManager().addActivity(this);
        setSystemBarTint(R.color.theme_color);
    }

    /**
     * 如果不想让标题栏变色，或者更改其它变色，重写此方法那可
     */
    protected void setSystemBarTint(int statusBarTintResource) {
        applyKitKatTranslucency(statusBarTintResource);
    }


    /**
     * 标题栏变色
     *
     * @param statusBarTintResource
     */
    private void applyKitKatTranslucency(int statusBarTintResource) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintResource(statusBarTintResource);//通知栏所需颜色
        }

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    /**
     * 显示横竖屏
     * ActivityInfo.SCREEN_ORIENTATION_PORTRAIT 只能竖屏
     * -1，可以横竖屏
     */
    protected void baseRequestedOrientation(int requestedOrientation) {
        if (requestedOrientation == -1) {
            return;
        } else {
            setRequestedOrientation(requestedOrientation);
        }
    }


    /**
     * 返回操作 子类可以覆盖此方法做特殊业务
     */
    protected void actionBack() {
        StartActUtils.finish(mContext);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getActivityManager().finishActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        actionBack();
    }

    private Boolean isExit = false;

    /**
     * 退出App
     */
    protected void exitApp() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            ToastUtils.show(mContext, ResUtils.getStr(R.string.app_exit));
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            stopService(new Intent(mContext, CheckAppUpdateService.class));//查检升级服务
            stopService(new Intent(mContext, AppDownloadService.class));//下载app升级服务
            //finish所有页面和kill app
            ActivityManager.getActivityManager().appExit(this);
        }
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    protected void verifyStoragePermissions() {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(mContext,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
