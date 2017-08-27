package com.ytd.common.ui.activity.actionbar;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.tlf.basic.uikit.dialog.widget.NormalDialog;
import com.tlf.basic.uikit.kprogresshud.KProgressHUD;
import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.ToastUtils;
import com.tlf.basic.utils.ViewFindUtils;
import com.ytd.common.ui.activity.BaseActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.presenter.IEquipmentPresenter;
import com.ytd.framework.equipment.presenter.IProperyPresenter;
import com.ytd.framework.equipment.presenter.impl.EquipmentPresenterImpl;
import com.ytd.framework.equipment.presenter.impl.ProperyPresenterImpl;
import com.ytd.framework.equipment.ui.activity.EquipmentScanDetailsResultActivity_;
import com.ytd.support.constants.fixed.ScannerInterface;
import com.ytd.uikit.actionbar.ActionBarView;
import com.ytd.uikit.actionbar.OnBackClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ytd on 15/12/17.
 */
public abstract class BaseScannerReceiverActivity extends BaseActivity {

    protected ActionBarView actionBarView;
    private static final String RES_ACTION = "android.intent.action.SCANRESULT";
    protected KProgressHUD hud;
    protected IEquipmentPresenter equipmentPresenter;
    protected IProperyPresenter properyPresenter;
    ScannerInterface scanner;
    IntentFilter intentFilter;
    BroadcastReceiver scanReceiver;
    NormalDialog dialog;
    private String testScanID = "020284500032086";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        equipmentPresenter = new EquipmentPresenterImpl();
        properyPresenter = new ProperyPresenterImpl();
        hud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f)
                .setLabel(mContext.getResources().getString(R.string.common_dialog_loading))
                .setCancellable(false);
        dialog = new NormalDialog(mContext);
        initScanner();
    }


    protected void initActionBar() {
        actionBarView = ViewFindUtils.find(this, R.id.actionbar);
        actionBarView.setOnBackClickListener(new OnBackClickListener() {
            @Override
            public void onClick(View v) {
                actionBack();
            }
        });
    }

    private void initScanner() {
        scanner = new ScannerInterface(this);
        scanner.resultScan();//iScan恢复默认设置
        scanner.enablePlayBeep(true);//是否允许蜂鸣反馈
        scanner.enableFailurePlayBeep(true);//扫描失败蜂鸣反馈
        scanner.enableAddKeyValue(1);/**附加无、回车、Teble、换行*/
        /**设置扫描结果的输出模式，参数为0和1：
         * 0为模拟输出（在光标停留的地方输出扫描结果）；
         * 1为广播输出（由应用程序编写广播接收者来获得扫描结果，并在指定的控件上显示扫描结果）
         * 这里采用接收扫描结果广播并在TextView中显示*/
        scanner.setOutputMode(1);

        //扫描结果的意图过滤器的动作一定要使用"android.intent.action.SCANRESULT"
        intentFilter = new IntentFilter(RES_ACTION);
        //注册广播接受者
        scanReceiver = new ScannerResultReceiver();
        registerReceiver(scanReceiver, intentFilter);
    }


    /**
     * 结束扫描
     */
    protected void finishScanner() {
        scanner.scan_stop();
        unregisterReceiver(scanReceiver);    //反注册广播接收者
    }

    /**
     * 扫描结果的广播接收者
     */
    protected class ScannerResultReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RES_ACTION)) {
                //获取扫描结果
                final String scanResult = intent.getStringExtra("value");
//                ToastUtils.show(mContext, "结果：" + scanResult);
                if (null != hud)
                    hud.show();
                //TODO
                /**
                 * 扫码ID是写死的，还有资产ID，到时修改
                 */

                findScanResult(testScanID);
            }
        }
    }


    //查询单
    protected void findScanResult(String scanResult) {
        try {
            List<EquipmentBean> list = equipmentPresenter.findScanCode(mContext, scanResult);
            if (ListUtils.isEmpty(list)) {
                hud.dismiss();
                ToastUtils.show(mContext, "没有找到您扫描的设备信息!");
            } else {
                PropertyBean propertyBean = properyPresenter.findById(mContext, list.get(0).getPDDH());
                hud.dismiss();
                Map<String, Object> map = new HashMap<>();
                map.put("bean", list.get(0));
                map.put("scanTag", 0);
                map.put("propertyBean", propertyBean);
                StartActUtils.start(mContext, EquipmentScanDetailsResultActivity_.class, map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            hud.dismiss();
            ToastUtils.show(mContext, "查找设备信息失败!");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishScanner();
    }

}
