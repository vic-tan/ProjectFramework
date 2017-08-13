package com.ytd.framework.main.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.ytd.common.ui.activity.actionbar.BaseActionBarActivity;
import com.ytd.framework.R;
import com.ytd.framework.main.adapter.HomeNavigatorFragmentAdapter;
import com.ytd.framework.main.ui.navigator.FragmentNavigator;
import com.ytd.framework.main.ui.view.HomeNavigatorView;
import com.ytd.uikit.actionbar.ActionBarOptViewTagLevel;
import com.ytd.uikit.actionbar.OnOptClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 首页界面
 * Created by ytd on 16/1/19.
 */
@EActivity(R.layout.main_activity_home)
public class HomeActivity extends BaseActionBarActivity implements HomeNavigatorView.OnBottomNavigatorViewItemClickListener {

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
                if (Build.VERSION.SDK_INT>22){
                    if (ContextCompat.checkSelfPermission(mContext,
                            android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                        //先判断有没有权限 ，没有就在这里进行权限的申请
                        ActivityCompat.requestPermissions((Activity) mContext,
                                new String[]{android.Manifest.permission.CAMERA},1000);

                    }else {
                        //说明已经获取到摄像头权限了 想干嘛干嘛
                        start();
                    }
                }else {
                    //这个说明系统版本在6.0之下，不需要动态获取权限。
                    start();
                }


            }
        });
        if (bottomNavigatorView != null) {
            bottomNavigatorView.setOnBottomNavigatorViewItemClickListener(this);
        }
        setCurrentTab(mNavigator.getCurrentPosition());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode){
            case 1000:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //这里已经获取到了摄像头的权限，想干嘛干嘛了可以
                    start();
                }else {
                    //这里是拒绝给APP摄像头权限，给个提示什么的说明一下都可以。
                    Toast.makeText(mContext,"请手动打开相机权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }

    private void start(){
        /*Intent intent = new Intent(mContext,EquipmentScanActivity.class);
                    StartActUtils.forResult(mContext,
                            intent,RESULT_OK);*/
        Intent intent = new Intent(mContext, CaptureActivity.class);
        startActivityForResult(intent, RESULT_OK);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         /* 处理二维码扫描结果
                */
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
    }

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


}
