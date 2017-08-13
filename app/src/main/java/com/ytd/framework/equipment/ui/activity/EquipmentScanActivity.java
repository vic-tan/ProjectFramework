package com.ytd.framework.equipment.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.ytd.common.ui.activity.actionbar.BaseActionBarActivity;
import com.ytd.framework.R;

/**
 * Created by ytd on 16/1/19.
 */
//@EActivity(R.layout.equipment_scan_activity)
public class EquipmentScanActivity extends BaseActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment_scan_activity);
        initActionBar();
        actionBarView.setVisibility(View.GONE);
        /**
         * 执行扫面Fragment的初始化操作
         */
        CaptureFragment captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.scan_camera_layout);

        captureFragment.setAnalyzeCallback(analyzeCallback);
        /**
         * 替换我们的扫描控件
         */ getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }


   /* @AfterViews
    void init() {

        actionBarView.setVisibility(View.GONE);

        *//**
         * 执行扫面Fragment的初始化操作
         *//*
        CaptureFragment captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.scan_camera_layout);

        captureFragment.setAnalyzeCallback(analyzeCallback);
        *//**
         * 替换我们的扫描控件
         *//* getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }*/






    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
          /*  Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            ToastUtils.show(mContext,result);
            resultIntent.putExtras(bundle);
            EquipmentScanActivity.this.setResult(RESULT_OK, resultIntent);*/
//            EquipmentScanActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            /*Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            ToastUtils.show(mContext,"失败");
            resultIntent.putExtras(bundle);
            EquipmentScanActivity.this.setResult(RESULT_OK, resultIntent);*/
//            EquipmentScanActivity.this.finish();
        }
    };


}
